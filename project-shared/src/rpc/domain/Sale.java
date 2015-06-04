
package rpc.domain;

import com.google.gson.annotations.SerializedName;
import rest.domain.Customer;
import java.util.Collection;


public class Sale {
    
    @SerializedName("id")
    private String id;
    @SerializedName("sale_date")
    private String date;
    @SerializedName("register_sale_products")
    private Collection<SaleItem> saleItems;
    private Customer cust;
    
    public Sale() {}

    public Sale(String id, String date, Collection<SaleItem> saleItems, Customer cust) {
        this.id = id;
        System.out.println("Sale constructor called, id is: " + id);
        this.date = date;
        this.saleItems = saleItems;
        this.cust = cust;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Collection<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(Collection<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }

    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }

    @Override
    public String toString() {
        return "Sale{" + "id=" + id + ", date=" + date + ", saleItems=" + saleItems + ", cust=" + cust + '}';
    }

    
    
    


    
    
}
