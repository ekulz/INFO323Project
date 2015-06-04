
package router;

import javax.jms.ConnectionFactory;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.Exchange;
import rest.domain.Transaction;
import rpc.domain.Sale;


public class MakePurchaseRouter {
    
    public static void main(String[] args) throws Exception {
        CamelContext camel = new DefaultCamelContext();
        //register activemq as jms handler
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        camel.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        //create the routes
        camel.addRoutes(new RouteBuilder() {
            
            @Override
            public void configure() {
                
                //from webhook message to vend-email queue
                from("imaps://outlook.office365.com?username=hiclu995@student.otago.ac.nz"
                    + "&password=" + retrievePw("Please enter your password")
                    + "&searchTerm.subject=Vend:SaleUpdate")
                        .log("New saleupdate found: ${body}")
                        .multicast()
                        .to("jms:queue:vend-email", "jms:queue:use-coupon");
                
                //from vend-email multicast to new-sale and new-transaction
                from("jms:queue:vend-email")
                        .setHeader("saleId").jsonpath("$.id")
                        .setHeader("customerId").jsonpath("$.customer_id")
                        .setHeader("totalPrice").jsonpath("$.totals.total_price")
                        .log("Total price is: ${headers.totalPrice}")
                        .multicast()
                        .to("jms:queue:new-sale", "jms:queue:new-transaction");
                
                //from new-sale unmarshal and call newSale on the sales aggregation service (RPC)
                //multicast a copy to a temp queue for debugging
                from("jms:queue:new-sale")
                        .log("trying to unmarshal json")
                        .unmarshal().json(JsonLibrary.Gson, Sale.class)
                        .log("successfully unmarshalled")
                        .to("cxf:http://localhost:9001/sales?serviceClass=salesagg.ISalesAggregationService&defaultOperationName=newSale")
                        .log("newSale called on RPC/SOAP");
                
                //from the new transaction caculate the total points earnt and send to calculated points
                from("jms:queue:new-transaction")
                        .setHeader("points").method(MakePurchaseRouter.class, "getPoints(${headers.totalPrice})")
                        .to("jms:queue:calculated-points");
                
                //from calculated points convert to transaction and send to send-transaction
                from("jms:queue:calculated-points")
                        .log("Transaction ID = ${headers.saleId}")
                        .bean(Transaction.class, "newTransaction(${headers.saleId}, ${headers.points})")
                        .log("Points in header = ${headers.points}")
                        .to("jms:queue:send-transaction");
                
                //from send-transaction marshal to JSON
                from("jms:queue:send-transaction")
                        .setProperty("customerId").header("customerId")  // copy resource ID to property (since we are about the delete the headers)
                        .setProperty("pointsGained").header("points")
                        .removeHeaders("*") // remove headers to stop them being sent to the service
                        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                        .marshal().json(JsonLibrary.Gson)
                        .recipientList().simple("http4://localhost:8081/customers/${exchangeProperty.customerId}/transactions");  // send to service
            }
            
        });
        
        camel.setTracing(false);
        System.out.println("Starting MakePurchaseRouter...");
        camel.start();
        System.out.println("...Ready");
    }
    
    public static String retrievePw(String message) {
        JPasswordField textPw = new JPasswordField();
        int resp = JOptionPane.showConfirmDialog(null, textPw, message,
                   JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resp == JOptionPane.OK_OPTION) {
            String password = new String(textPw.getPassword());
            return password;
        }
        return null;
    }
    
    public Integer getPoints(Double totalPrice) {
        return (totalPrice.intValue())/10;
    }
    
}
