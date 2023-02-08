package com.interview.interview.DTO;
public class OrderRequest {
    private Double orderAmount;

    public OrderRequest() {
    }

    public OrderRequest(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

}
