package router;

import com.google.gson.Gson;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class Client {
    
    private static final String sale = "{\"id\":\"2ed7950a-a0fb-bcc7-11e4-f90f26de7276\",\"sale_date\":\"2015-05-13T01:28:55Z\",\"status\":\"CLOSED\",\"user_id\":\"b8ca3a6e-7276-11e4-fd8a-e890c303b609\",\"customer_id\":\"b8ca3a6e-7276-11e4-fd8a-ead47df29161\",\"register_id\":\"b8ca3a6e-7276-11e4-fd8a-e890a8e057b8\",\"market_id\":\"1\",\"invoice_number\":\"3\",\"short_code\":\"rjb1hk\",\"totals\":{\"total_price\":\"15.6087\",\"total_tax\":\"2.34131\",\"total_payment\":\"17.95\",\"total_to_pay\":\"0\"},\"note\":null,\"updated_at\":\"2015-05-13 01:28:55\",\"created_at\":\"2015-05-13 01:28:55\",\"customer\":{\"id\":\"b8ca3a6e-7276-11e4-fd8a-ead47df29161\",\"customer_code\":\"jimbob\",\"balance\":\"0.000\",\"points\":0,\"note\":\"\",\"year_to_date\":\"209.39999\",\"sex\":null,\"date_of_birth\":null,\"custom_field_1\":\"\",\"custom_field_2\":\"\",\"custom_field_3\":\"\",\"custom_field_4\":\"\",\"updated_at\":\"2015-05-12 03:23:17\",\"created_at\":\"2015-04-24 22:51:49\",\"deleted_at\":null,\"contact_first_name\":\"Jim\",\"contact_last_name\":\"Bob\"},\"user\":{\"id\":\"b8ca3a6e-7276-11e4-fd8a-e890c303b609\",\"name\":\"hiclu995\",\"display_name\":\"Luke Hickin\",\"email\":\"hiclu995@student.otago.ac.nz\",\"outlet_id\":\"b8ca3a6e-7276-11e4-fd8a-e890a0a98c15\",\"target_daily\":null,\"target_weekly\":null,\"target_monthly\":null,\"created_at\":\"2015-04-22 01:41:57\",\"updated_at\":\"2015-05-13 01:26:54\"},\"register_sale_products\":[{\"id\":\"2ed7950a-a0fb-90ff-11e4-f90f6138e1c1\",\"product_id\":\"bc305bf5-da20-11e4-f3a2-b575f2cb0159\",\"quantity\":1,\"price\":\"2.60870\",\"price_set\":false,\"tax\":\"0.39131\",\"price_total\":\"2.6087\",\"tax_total\":\"0.39131\",\"tax_id\":\"bc305bf5-da20-11e4-f3a2-b575f2bd6715\"},{\"id\":\"2ed7950a-a0fb-90ff-11e4-f90f6249a7ea\",\"product_id\":\"bc305bf5-da20-11e4-f3a2-b575f2c817d5\",\"quantity\":1,\"price\":\"13.00000\",\"price_set\":false,\"tax\":\"1.95000\",\"price_total\":\"13\",\"tax_total\":\"1.95\",\"tax_id\":\"bc305bf5-da20-11e4-f3a2-b575f2bd6715\"}],\"register_sale_payments\":[{\"id\":\"2ed7950a-a0fb-90ff-11e4-f90f6b8ec5eb\",\"payment_date\":\"2015-05-13T01:28:55Z\",\"amount\":17.95,\"retailer_payment_type_id\":\"bc305bf5-da20-11e4-f3a2-b575f2d36ffc\",\"payment_type_id\":1,\"retailer_payment_type\":{\"id\":\"bc305bf5-da20-11e4-f3a2-b575f2d36ffc\",\"name\":\"Cash\",\"payment_type_id\":\"1\",\"config\":null},\"payment_type\":{\"id\":\"1\",\"name\":\"Cash\",\"has_native_support\":false},\"register_sale\":{\"id\":\"2ed7950a-a0fb-bcc7-11e4-f90f26de7276\",\"sale_date\":\"2015-05-13T01:28:55Z\",\"status\":\"CLOSED\",\"user_id\":\"b8ca3a6e-7276-11e4-fd8a-e890c303b609\",\"customer_id\":\"b8ca3a6e-7276-11e4-fd8a-ead47df29161\",\"register_id\":\"b8ca3a6e-7276-11e4-fd8a-e890a8e057b8\",\"market_id\":\"1\",\"invoice_number\":\"3\",\"short_code\":\"rjb1hk\",\"totals\":{\"total_price\":\"15.6087\",\"total_tax\":\"2.34131\",\"total_payment\":\"17.95\",\"total_to_pay\":\"0\"},\"note\":null,\"updated_at\":\"2015-05-13 01:28:55\",\"created_at\":\"2015-05-13 01:28:55\"}}],\"taxes\":[{\"id\":\"f2d18656-b575-11e4-b3a2-bc305bf5da20\",\"name\":\"GST\",\"rate\":0.15,\"tax\":2.34131}]}";
    public static void main(String[] args) {
        
        /* Basic camel setup */
        // create default context
        CamelContext camel = new DefaultCamelContext();

        // register ActiveMQ as the JMS handler
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        camel.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        /* End basic setup */
        
        // create message producer
        ProducerTemplate producer = camel.createProducerTemplate();
        Gson gson = new Gson();
        String jsonSale = gson.toJson(sale);
        // send a message
        producer.sendBody("jms:queue:vend-email", jsonSale);
        System.out.println("Sent JSON to queue vend-email");
    }
}
