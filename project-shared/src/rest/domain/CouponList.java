package rest.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public class CouponList implements Serializable {
    
    private Collection<Coupon> coupons = new HashSet<>();
    
    public CouponList(Collection newCoupons) {
        this.coupons.addAll(newCoupons);
    }
    
    public void addCoupon(Coupon c) {
        coupons.add(c);
    }
    
    public Collection<Coupon> getCoupons() {
        return coupons;
    }
    
    public void setCoupons(List<Coupon> c) {
        this.coupons = c;
    }

    @Override
    public String toString() {
        return "CouponList{" + "coupons=" + coupons + '}';
    }
    
    
}
