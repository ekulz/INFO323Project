package router;

import javax.jms.ConnectionFactory;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;


public class CreateCouponRouter {
    
    public static void main(String[] args) throws Exception {
        String vendUsername = "hiclu995";
        String vendPassword = "zanyberry80";
        //String vendPassword = retrievePw("Enter your Vend password");

        UsernamePasswordCredentials vendCreds 
            = new UsernamePasswordCredentials(vendUsername, vendPassword);

        // create the Base 64 encoded Basic Access header
        String basicAuthHeader 
            = BasicScheme.authenticate(vendCreds, "US-ASCII", false).getValue();
        
        //create default context
        CamelContext camel = new DefaultCamelContext();
        
        //register activemq as jms handler
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        camel.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        //create the routes
        camel.addRoutes(new RouteBuilder() {

            @Override
            public void configure() {
                
                from("websocket://localhost:9002/email")
                        // copy current message body into a property so we don't lose it
                        .setProperty("email").simple("${body.trim()}")
                        .log("Set property")
                        .removeHeaders("*")
                        .log("Headers removed")
                        // add basic access authentication header
                        .setHeader("Authorization", constant(basicAuthHeader))
                        .log("Authorization header set")
                        // GET requests have no body, so remove it to avoid problems
                        .setBody(constant(null))
                        .log("Body removed")
                        .setHeader(Exchange.HTTP_METHOD, constant("GET")) 
                        // send to authentication service
                        .to("http4://info-nts-12.otago.ac.nz:8086/vend-oauth/restricted/token")
                        .log("Sent to authentication service")
                        // copy token from response body into a header
                        .convertBodyTo(String.class) // body needs to be a string so we can trim it  
                        .log("Body converted to string")
                        // copy token into OAuth authentication header
                        .setHeader("Authorization").simple("Bearer ${body.trim()}") // trim is necessary!
                        .log("Set header authorization and trim the body")
//                        // put original message body back
//                        .setBody().exchangeProperty("originalBody")
//                        .removeProperty("originalBody")
                        .to("direct:send-to-vend")
                        .log("called direct:send-to-vend");
                
                
                from("direct:send-to-vend")
                        // remove all headers except Authorization
                        .log("reached direct:send-to-vend")
                        .removeHeaders("*", "Authorization")
                        .log("removed headers")
                        // marshal to JSON
                        .marshal().json(JsonLibrary.Gson)
                        .log("marshalled to json")
                        .setHeader(Exchange.CONTENT_TYPE).constant("application/json")
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        // send to Vend
                        .log("send to vend")
                        .log("${exchangeProperty.email}")
                        //.to("http://info323.vendhq.com/api/<API path>")
                        //.recipientList().simple("http4://localhost:9080/api/customers?email=${exchangeProperty.email}")  
                        .recipientList().simple("https://info323.vendhq.com/api/customers?email=${exchangeProperty.email}")  
                        .log("recipient list called")
                        // put Vend's reponse in a queue
                        .to("jms:queue:vend-customer");
                        
                
                from("jms:queue:vend-customer")
                        //convert body to string
                        .convertBodyTo(String.class)
                        .to("websocket://localhost:9002/email?sendToAll=true")
                        .log("customer JSON (in a string) sent via websocket");
                
                
                from("websocket://localhost:9002/product")
                        // copy current message body into a property so we don't lose it
                        .setProperty("coupon").simple("${body}")
                        .log("Set property coupon complete")
                        .setBody(constant(null))
                        .log("Set body to null")
                        
                        // add basic access authentication header
                        .setHeader("Authorization", constant(basicAuthHeader))
                        .log("Authorization header set")
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        // send to authentication service
                        .to("http4://info-nts-12.otago.ac.nz:8086/vend-oauth/restricted/token")                        
                        .log("Sent to authentication service")
                        
                        .convertBodyTo(String.class) // body needs to be a string so we can trim it  
                        .log("Body converted to string")
                        .setHeader("Authorization").simple("Bearer ${body.trim()}") // trim is necessary!
                        .setBody(constant(null))
                        .setBody().header("coupon")
                        .removeHeaders("*", "Authorization")
                        .log("Coupon headers removed")
                        .setHeader(Exchange.CONTENT_TYPE).constant("application/json")
                        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                        .log("post headers set")
                        
                        //send to vend
                        .multicast()
                        .to("https://info323.vendhq.com/api/products", "jms:queue:vend-coupon")
                        .log("sent to vend");
                
                from("jms:queue:vend-coupon")
                        .convertBodyTo(String.class)
                        .log("Converted body to string")
                        .to("websocket://localhost:9002/product?sendToAll=true")
                        .log("send to websocket");
            }
        });
        
        // turn exchange tracing on or off (false is off)  
		camel.setTracing(false);

		// start routing  
		System.out.println("Starting CreateCouponRouter...");
		camel.start();
		System.out.println("... ready.");
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
}
