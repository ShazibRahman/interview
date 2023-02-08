package com.interview.interview.repository.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.Date;

@Document
public class Order {
    @Id
    private String id; // Order ID

    @Field
    private String customerId;

    @Field
    private Double orderAmount;

    @Field
    private Double discount = 0d;

    @Field
    private String orderStatus = "PAID"; // initialiation of order status
    @Field
    private Date OrderDate = new Date();

    public Order() {
    }


    public Order(String id, String customerId, Double orderAmount, Double discount, Date orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.orderAmount = orderAmount;
        this.discount = discount;
        OrderDate = orderDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
