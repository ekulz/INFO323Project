package rest.domain;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Customer implements Serializable {
    
    @SerializedName("id")
    private String custId;
    @SerializedName("sex")
    private String gender;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    private static Map<Integer, Coupon> couponMap = null;
    private static Map<String, Transaction> transactionMap = null;
    
//    public Customer() {
//        if (couponMap == null) {
//            couponMap = new HashMap();
//            couponMap.put(1, new Coupon(1, 0, false));
//        }
//        if (transactionMap == null) {
//            transactionMap = new HashMap();
//            transactionMap.put("1", new Transaction("1", "shop1", 0));
//        }
//    }
    
    public Customer() {
        if (couponMap == null)
            couponMap = new HashMap();
        if (transactionMap == null)
            transactionMap = new HashMap();
    }

    public Customer(String custId) {
        if (couponMap == null)
            couponMap = new HashMap();
        if (transactionMap == null)
            transactionMap = new HashMap();
        this.custId = custId;
    }
    
    public Customer(String gender, String dateOfBirth) {
        if (couponMap == null)
            couponMap = new HashMap();
        if (transactionMap == null)
            transactionMap = new HashMap();
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
    
    public Customer(String id, String gender, String dateOfBirth) {
        if (couponMap == null)
            couponMap = new HashMap();
        if (transactionMap == null)
            transactionMap = new HashMap();
        this.custId = id;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    
    
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public static Map<Integer, Coupon> getCouponMap() {
        return couponMap;
    }
     

    public static Map<String, Transaction> getTransactionMap() {
        return transactionMap;
    }
    
    public void addCoupon(Coupon coupon) {
        couponMap.put(coupon.getId(), coupon);
    }
    
    public Coupon getCouponById(Integer id) {
        return couponMap.get(id);
    }
    
    public void deleteCoupon(Coupon coupon) {
        couponMap.remove(coupon.getId());
    }

    public void updateCoupon(Integer id, Coupon updatedCoupon) {
        couponMap.put(id, updatedCoupon);
    }

    public boolean couponExists(Integer id) {
        return couponMap.containsKey(id);
    }
   
    public void addTransaction(Transaction transaction) {
        transactionMap.put(transaction.getId(), transaction);
    }
    
    public Transaction getTransactionById(String id) {
        return transactionMap.get(id);
    }
    
    public void deleteTransaction(Transaction transaction) {
        transactionMap.remove(transaction.getId());
    }

    public void updateTransaction(String id, Transaction updatedTransaction) {
        transactionMap.put(id, updatedTransaction);
    }

   public boolean transactionExists(String id) {
      return transactionMap.containsKey(id);
   }
   
   public TransactionList getTransactionList() {
       return new TransactionList(transactionMap.values());
   }
   
   public CouponList getCouponList() {
       return new CouponList(couponMap.values());
   }

    @Override
    public String toString() {
        return "Customer{" + "custId=" + custId + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + '}';
    }


   
   

    
    
}
