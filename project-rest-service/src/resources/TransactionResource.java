package resources;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import rest.dao.CustomerDAO;
import rest.domain.Customer;
import rest.domain.Transaction;

@Path("/customers/{customer-id}/transactions/{id}")
public class TransactionResource {

    private CustomerDAO dao = new CustomerDAO();
    private Customer customer = new Customer();
    private Transaction transaction;


    public TransactionResource(@PathParam("id") String transactionId, @PathParam("customer-id") String custId) {
        customer = dao.getCustomerById(custId);
        if (customer.transactionExists(transactionId)) {
            this.transaction = customer.getTransactionById(transactionId);
        } else {
            throw new NotFoundException("No transaction matches this ID");
        }
    }

    @GET
    public Transaction getTransaction() {
        return transaction;
    }
    
}