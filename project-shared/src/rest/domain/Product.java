
package rest.domain;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class Product implements Serializable{
    @SerializedName("source_id")
    private String source_id;
    @SerializedName("handle")
    private String handle;
    @SerializedName("type")
    private String type;
    @SerializedName("tags")
    private String tags;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("sku")
    private String sku;
    @SerializedName("retain_price")
    private Double retail_price;
    
    public Product addProduct(String source_id, String handle, String type, String tags, String name, String description, String sku, Double retail_price){
        Product p = new Product(source_id, handle, type, tags, name, description, sku, retail_price);
        return p;
    }

    public Product(String source_id, String handle, String type, String tags, String name, String description, String sku, Double retail_price) {
        this.source_id = source_id;
        this.handle = handle;
        this.type = type;
        this.tags = tags;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.retail_price = retail_price;
    }

    public Product() {}

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(Double retail_price) {
        this.retail_price = retail_price;
    }

    @Override
    public String toString() {
        return "Product{" + "source_id=" + source_id + ", handle=" + handle + ", type=" + type + ", tags=" + tags + ", name=" + name + ", description=" + description + ", sku=" + sku + ", retail_price=" + retail_price + '}';
    }
    
    
    
}
