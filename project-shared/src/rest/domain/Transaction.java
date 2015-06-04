package rest.domain;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class Transaction implements Serializable {
    
    private String id;
    private final String shop = "Shop1";
    @SerializedName("points")
    private Integer points;

    public Transaction() {
    }

    public Transaction(String id, Integer points) {
        this.id = id;
        this.points = points;
    }
    
    public Transaction newTransaction(String s, Integer i) {
        Transaction t = new Transaction(s, i);
        return t;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShop() {
        return shop;
    }

//    public void setShop(String shop) {
//        this.shop = shop;
//    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", shop=" + shop + ", points=" + points + '}';
    }
    
    
    
}
