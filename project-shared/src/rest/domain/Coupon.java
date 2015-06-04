package rest.domain;

import java.io.Serializable;


public class Coupon implements Serializable {
    
    private Integer id;
    private Integer points;
    private Boolean used;

    public Coupon() {
    }

    public Coupon(Integer id, Integer points, Boolean used) {
        this.id = id;
        this.points = points;
        this.used = used;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getUsed() {
        return this.used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "Coupon{" + "id=" + id + ", points=" + points + ", used=" + used + '}';
    }

    void remove(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
