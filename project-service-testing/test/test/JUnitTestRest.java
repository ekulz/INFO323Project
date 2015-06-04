package test;

import rest.domain.Customer;
import rest.domain.Transaction;
import rest.domain.Coupon;
import rest.domain.TransactionList;
import rest.domain.CouponList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;



public class JUnitTestRest {
    
    Customer customer1;
    Transaction transaction1;
    Coupon coupon1, coupon11;
    TransactionList transactionList1;
    CouponList couponList1;
    WebTarget customers, transaction, coupon, totalPoints, unusedPoints;
    
    
    public JUnitTestRest() {
    }
    
    @Before
    public void setUp() {
        //create client configuration
        ClientConfig config = new ClientConfig();
        //create client endpoint
        Client client = ClientBuilder.newClient(config);
        
        //initialise web target for root service
        customers = client.target("http://localhost:8081/customers");
        transaction = client.target("http://localhost:8081/customers/1/transactions");
        coupon = client.target("http://localhost:8081/customers/1/coupons");
        totalPoints = client.target("http://localhost:8081/customers/1/points/total");
        unusedPoints = client.target("http://localhost:8081/customers/1/points/unused");
        
        customer1 = new Customer("1");
        transaction1 = new Transaction("1", 3);
        customer1.addTransaction(transaction1);
        coupon1 = new Coupon(1, 1, false);
        coupon11 = new Coupon(2, 1, false);
        customer1.addCoupon(coupon1);
        customer1.addCoupon(coupon11);
        
        System.out.println(customer1.getCouponList());

        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCustomerGet() {
        Customer testCust = customers.path("1").request("application/json")
                .get(Customer.class);
        //compare
        //System.out.println("CUSTOMER 1 " + customer1.toString());
        //System.out.println("TEST CUSTOMER " + testCust.toString());
        assertEquals(customer1.toString(), testCust.toString());
    }
    
    @Test
    public void testTransactionGet() {
        Transaction testTran = transaction.path("1").request("application/json")
                .get(Transaction.class);
        //compare
        //System.out.println("TRANSACTION 1 " + transaction1.toString());
        //System.out.println("TESTRANSACTION " + testTran.toString());
        assertEquals(testTran.toString(), transaction1.toString());
        
    }
    
    @Test
    public void testTransactionListGet() {
        String rawIds = transaction.request("application/json").get(String.class);
        assertTrue(rawIds.contains("\"id\":\"" + transaction1.getId()));
    }
    
    @Test
    public void testCouponGet() {
        
        Coupon testCoupon = coupon.path("2").request("application/json")
                .get(Coupon.class);
        //compare
        assertEquals(testCoupon.toString(), coupon11.toString());
    }
    
    @Test
    public void testCouponListGet() {
        String rawIds = coupon.request("application/json").get(String.class);
        System.out.println(rawIds);
        assertTrue(rawIds.contains("\"id\":" + coupon1.getId()));
    }
    
    @Test
    public void testPointsTotalGet() {
        int totalTestPoints = totalPoints.request("text/plain")
                .get(Integer.class);
        //compare
        assertEquals(totalTestPoints, 3);
    }
    
    @Test
    public void testPointsUnusedGet() {
        int unusedTestpoints = unusedPoints.request("text/plain")
                .get(Integer.class);
        //compare
        assertEquals(unusedTestpoints, 1);
    }
    
    @Test
    public void testTransactionListPost() {
        //create new transaction (different than hardcoded values
        Transaction transaction2 = new Transaction("99", 1);
        customer1.addTransaction(transaction2);
        //post the new transaction
        transaction.request().post(Entity.entity(transaction2, "application/json"));
        Transaction testTran = transaction.path("99").request("application/json")
                .get(Transaction.class);
        //compare
        assertEquals(transaction2.toString(), testTran.toString());
    }
    
    @Test
    public void testCouponListPost() {
        //create new coupon with diff values than the hardcoded values
        Coupon couponTest = new Coupon(99, 1, false);
        customer1.addCoupon(couponTest);
        //post coupon
        coupon.request().post(Entity.entity(couponTest, "application/json"));
        Coupon testCoupon = coupon.path("99").request("application/json")
                .get(Coupon.class);
        //compare
        //System.out.println(coupon2.toString() + " " + testCoupon.toString());
        assertEquals(couponTest.toString(), testCoupon.toString());
    }
    
    @Test
    public void testCouponPut() {
        coupon1.setUsed(true);
        coupon.path("1").request().put(Entity.entity(coupon1, "application/json"));
        //compare coupons
        //System.out.println("Coupon1: " + coupon1.toString());
        Coupon testCoupon = coupon.path("1").request("application/json")
                .get(Coupon.class);
        
        assertEquals(true, testCoupon.getUsed());
    }
    
}
