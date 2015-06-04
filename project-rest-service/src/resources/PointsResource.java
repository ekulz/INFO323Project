package resources;

import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import rest.domain.Coupon;
import rest.domain.Customer;
import rest.domain.Transaction;


@Path("/customers/{customer-id}/points")
public class PointsResource {
    

    private Customer customerDao = new Customer();
    private Collection<Transaction> t;
    private Collection<Coupon> c;
    private int total;
    private int unused;
    
    public PointsResource() {}
    
    @GET @Path("/total")
    public int getTotal() {
        t = customerDao.getTransactionList().getTransactions();
        for (Transaction tr : t) {
            total += tr.getPoints();
        }
        return total;
    }
  
    @GET @Path("/unused")
    public int getUnused() {
        unused += getTotal();
        c = customerDao.getCouponList().getCoupons();
        for (Coupon co : c) {
            unused -= co.getPoints();
        }
        return unused;
    }
}
