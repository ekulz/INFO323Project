package resources;

import java.util.Collection;
import java.util.HashSet;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import rest.domain.Customer;
import rest.domain.Transaction;
import rest.domain.TransactionList;

@Path("/customers/{customer-id}/transactions")
public class TransactionListResource {
    
    private Customer customerDao = new Customer();
    private Transaction t;

    public TransactionListResource() {
    }
    
    
    @GET
    public Collection<Transaction> getTransactionList() {
        System.out.println("getTransactionList called on REST service");
        return customerDao.getTransactionList().getTransactions();
        
    }
    
    @POST
    public void addTransaction(Transaction t) {
        System.out.println("addTransaction called on REST service");
        System.out.println(t);
        customerDao.addTransaction(t);
    }
    
}
