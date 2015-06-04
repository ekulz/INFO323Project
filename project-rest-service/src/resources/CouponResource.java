package resources;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import rest.domain.Coupon;
import rest.domain.Customer;

@Path("/customers/{customer-id}/coupons/{id}")
public class CouponResource {
    
    private Customer customerDao = new Customer();
    private Coupon coupon;
    private int couponId;
    
    
    public CouponResource(@PathParam("id") Integer couponId) {
        this.couponId = couponId;
        
        if (customerDao.couponExists(couponId)) {
            this.coupon = customerDao.getCouponById(couponId);
        } else {
            throw new NotFoundException("No coupon matches this ID");
        }
    }
    
    @GET
    public Coupon getCoupon() {
        return coupon;
    }
    
    @PUT
    public Response updateCoupon(Coupon updatedCoupon) {
        if (this.coupon.getId() == updatedCoupon.getId()) {
            coupon.setUsed(true);
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity("IDs don't match")
                    .build();
        } else {
            return Response.noContent().build();
        }
    }
    
}
