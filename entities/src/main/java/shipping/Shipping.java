package shipping;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer shippingId;

    @Column(nullable = false, unique = true)
    private Integer orderId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false)
    @ElementCollection
    private Map<Integer,Integer> products;

    @Column(nullable = false)
    private String status;

    private Integer DDT;

    public Integer getShippingId() {
        return shippingId;
    }

    public Shipping setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
        return this;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Integer getDDT() {
        return DDT;
    }

    public Shipping setDDT(Integer DDT) {
        this.DDT = DDT;
        return this;
    }

    public Shipping setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Shipping setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public Shipping setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public Shipping setProducts(Map<Integer, Integer> products) {
        this.products = products;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Shipping setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "The Shipping (with id = "+ shippingId +")" + " has: " +
                "\norderId = " + orderId +
                "\nuserId = " + userId +
                "\nshippingAddress = " + shippingAddress +
                "\nproducts = " + products +
                "\nstatus = " + status;
    }

}