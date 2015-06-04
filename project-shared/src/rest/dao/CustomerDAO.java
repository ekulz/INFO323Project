package rest.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import rest.domain.Coupon;
import rest.domain.Customer;
import rest.domain.Transaction;

public class CustomerDAO {
    
    private static Map<String, Customer> customerMap = null;
    
    public CustomerDAO() {
        if (customerMap == null) {
            customerMap = new HashMap();
            Customer customer1 = new Customer("1");
            //Transaction transaction1 = new Transaction("1", "Shop1", 1);
            customer1.addTransaction(new Transaction("1", 3));
            customer1.addCoupon(new Coupon(1, 1, false));
            customer1.addCoupon(new Coupon(2, 1, false));
            customerMap.put("1", customer1);
        }
    }

    public static Map<String, Customer> getCustomerMap() {
        return customerMap;
    }
    
    public Collection<Customer> getAll() {
        return customerMap.values();
    }
    
    public Customer getCustomerById(String id) {
        return customerMap.get(id);
    }
    
    public void addCustomer(String id, Customer customer) {
        customerMap.put(id, customer);
    }
    
    public void deleteCustomer(Customer customer) {
        customerMap.remove(customer);
    }
    
    public void customerExists(String id) {
        customerMap.containsKey(id);
    }
    
}
