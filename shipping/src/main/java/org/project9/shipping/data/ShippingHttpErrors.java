package org.project9.shipping.data;

public class ShippingHttpErrors {

    Long timestamp;
    String sourceIp;
    String service = "shipping";
    String request;
    String error;

    public Long getTimestamp() {
        return timestamp;
    }

    public ShippingHttpErrors setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public ShippingHttpErrors setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
        return this;
    }

    public String getService() {
        return service;
    }

    public ShippingHttpErrors setService(String service) {
        this.service = service;
        return this;
    }

    public String getRequest() {
        return request;
    }

    public ShippingHttpErrors setRequest(String request) {
        this.request = request;
        return this;
    }

    public String getError() {
        return error;
    }

    public ShippingHttpErrors setError(String error) {
        this.error = error;
        return this;
    }

    @Override
    public String toString() {
        return "ShippingHttpErrors{" +
                "timestamp=" + timestamp +
                ", sourceIp='" + sourceIp + '\'' +
                ", service='" + service + '\'' +
                ", request='" + request + '\'' +
                ", error='" + error + '\'' +
                '}';
    }

}
