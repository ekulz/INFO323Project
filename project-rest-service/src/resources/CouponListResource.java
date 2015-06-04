
package resources;

import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import rest.domain.Coupon;
import rest.domain.Customer;


@Path("/customers/{customer-id}/coupons")
public class CouponListResource {
    
    private Customer customerDao = new Customer();
    
    @GET
    public Collection<Coupon> getCouponList() {
       System.out.println("getCouponList called on REST service");
       return customerDao.getCouponList().getCoupons();
    }
    
    @POST
    public void addCoupon(Coupon c) {
        System.out.println("addCoupon called on REST service");
        System.out.println(c);
        customerDao.addCoupon(c);
    }
    
}
