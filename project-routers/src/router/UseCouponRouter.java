package router;

import java.util.Map;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;

public class UseCouponRouter {
    
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
        
        
        camel.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                
                
                //(we multicasted back in router 1)
                //converts sale into map message
                from("jms:queue:use-coupon")
                        .setHeader("custId").jsonpath("$.customer_id")
                        .unmarshal().json(JsonLibrary.Gson, Map.class)
                        .log("Customer id is set to header")
                        .to("jms:queue:products");
                
                //split the map into products
                from("jms:queue:products")
                        // perform the split
                        .split()
                        // get the collection to split
                        .simple("${body.[register_sale_products]}")
                        // send the split messages to next endpoint
                        .to("jms:queue:split-products");
                
                //marshal to json, then set product id as header
                from("jms:queue:split-products")
                        .marshal().json(JsonLibrary.Gson)
                        //set header
                        .setHeader("productId").jsonpath("$.product_id")
                        .log("set prodId to header")
                        .unmarshal().json(JsonLibrary.Gson, Map.class)
                        .log("convert to map message again")
                        .to("jms:queue:vend-auth");
                
                //vend authentication
                from("jms:queue:vend-auth")
                        .setProperty("productid").header("productId")
                        .setProperty("orignalbody").body()
                        .removeHeaders("*", "customerId", "productId")
                        // add basic access authentication header
                        .setHeader("Authorization", constant(basicAuthHeader))
                        // GET requests have no body, so remove it to avoid problems
                        .setBody(constant(null))
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        // send to authentication service
                        .to("http4://info-nts-12.otago.ac.nz:8086/vend-oauth/restricted/token")
                        // copy token from response body into a header
                        .convertBodyTo(String.class) // body needs to be a string so we can trim it
                        // copy token into OAuth authentication header
                        .setHeader("Authorization").simple("Bearer ${body.trim()}") // trim is necessary!
                        .setBody(constant(null))
                        
                        .setBody().header("originalbody")
                        .removeProperty("originalbody")
                        .removeHeaders("*", "Authorization", "productId", "customerId")
                        //.setHeader("Authorization", constant(basicAuthHeader))
                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                        .log("retrieving product from vend")
                        .recipientList().simple("https://info323.vendhq.com/api/products/${exchangeProperty.productid}")
                        .to("jms:queue:product-json")
                        .log("sent to product-json");

//                        // copy current message body into a property so we don't lose it
//                        .setProperty("prodId").header("prodId")
//                        .setProperty("obody").body()
//                        .log("Set prodId property and body property")
//                        .removeHeaders("*", "custId", "prodId")
//                        .log("Headers removed")
//                        // add basic access authentication header
//                        .setHeader("Authorization", constant(basicAuthHeader))
//                        .log("Authorization header set")
//                        // GET requests have no body, so remove it to avoid problems
//                        .setBody(constant(null))
//                        .log("Body removed")
//                        .setHeader(Exchange.HTTP_METHOD, constant("GET")) 
//                        // send to authentication service
//                        .to("http4://info-nts-12.otago.ac.nz:8086/vend-oauth/restricted/token")
//                        .log("Sent to authentication service")
//                        // copy token from response body into a header
//                        .convertBodyTo(String.class) // body needs to be a string so we can trim it  
//                        .log("Body converted to string")
//                        // copy token into OAuth authentication header
//                        .setHeader("Authorization").simple("Bearer ${body.trim()}") // trim is necessary!
//                        .log("trimmed the body")
//                        .setBody(constant(null))
//                        // put original message body back
//                        .setBody().header("obody")
//                        .log("set the body back in")
//                        .removeProperty("obody")
//                        .log("removed the body property")
//                        .removeHeaders("*", "Authorization", "prodId", "custId")
//                        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
//                        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
//                        .log("${exchangeProperty.prodId}")
//                        //.convertBodyTo(String.class)
//                        .recipientList().simple("https://localhost:9080/api/products/${exchangeProperty.prodId}")
//                        .log("got the product from vend")
//                        .to("jms:queue:product-json")
//                        .log("send to product-json");
                        
                //content based router for sorting coupons and products
                from("jms:queue:product-json")
                        .unmarshal().json(JsonLibrary.Gson, Map.class)
                        .split().simple("${body[products]}")
                        .choice()
                        .when().simple("${body[type]} == 'Coupon'")
                        .to("jms:queue:coupons")
                        .otherwise()
                        .to("jms:queue:not-coupons");
                
                
            }
        });
        
        camel.setTracing(false);
        System.out.println("Starting UseCouponRouter...");
        camel.start();
        System.out.println("...Ready");
        
    }
    
}
