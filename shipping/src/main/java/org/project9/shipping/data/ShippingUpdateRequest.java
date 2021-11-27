package org.project9.shipping.data;

import java.io.Serializable;

public class ShippingUpdateRequest implements Serializable {

    private Integer status;
    private Integer orderId;
    private Long timestamp;

    public Integer getStatus() {
        return status;
    }

    public ShippingUpdateRequest setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public ShippingUpdateRequest setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public ShippingUpdateRequest setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return "ShippingUpdateRequest{" +
                "status=" + status +
                ", orderId=" + orderId +
                ", timestamp=" + timestamp +
                '}';
    }

}
