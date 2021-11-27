package org.project9.shipping.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShippingCreateRequest implements Serializable {

    private Integer orderId;
    private Map<Integer,Integer> products = new HashMap<>();
    private Double total;
    private String shippingAddress;
    private String billingAddress;
    private Integer userId;

    public Integer getOrderId() {
        return orderId;
    }

    public ShippingCreateRequest setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public ShippingCreateRequest setProducts(Map<Integer, Integer> products) {
        this.products = products;
        return this;
    }

    public Double getTotal() {
        return total;
    }

    public ShippingCreateRequest setTotal(Double total) {
        this.total = total;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public ShippingCreateRequest setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public ShippingCreateRequest setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public ShippingCreateRequest setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "ShippingCreateRequest{" +
                "orderId=" + orderId +
                ", products=" + products +
                ", total=" + total +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", userId=" + userId +
                '}';
    }

}
