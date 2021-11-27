package org.project9.shipping.data;

import java.io.Serializable;

public class ShippingUpdateInvoicing implements Serializable {

    private Integer userId;
    private Integer orderId;
    private Double amountPaid;
    private Long timestamp;

    public Integer getUserId() {
        return userId;
    }

    public ShippingUpdateInvoicing setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public ShippingUpdateInvoicing setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public ShippingUpdateInvoicing setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public ShippingUpdateInvoicing setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "ShippingUpdateInvoicing{" +
                "userId=" + userId +
                ", orderId=" + orderId +
                ", amountPaid=" + amountPaid +
                ", timestamp=" + timestamp + '\'' +
                '}';
    }

}
