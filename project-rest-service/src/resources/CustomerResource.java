package resources;

import rest.dao.CustomerDAO;
import rest.domain.Customer;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/customers/{customer-id}")
public class CustomerResource {

    private CustomerDAO customer = new CustomerDAO();
    private Customer cust;

    public CustomerResource(@PathParam("customer-id") String customerId) {
        this.cust = customer.getCustomerById(customerId);
    }
    
    
    @GET
    public Customer getCustomer() {
        System.out.println("getCustomer called on REST service");
        return cust;
    }
    
    
    
}
