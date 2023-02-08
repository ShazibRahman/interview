package com.interview.interview.repository.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.interview.interview.enums.CustomerType;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.Date;

@Document
public class Customer {
    @Id
    private String id;
    @Field
    private String password;

    @Field
    private String userName;
    @Field
    private String email;
    @Field
    private int numOrders = 0;
    @Field
    private CustomerType customerType;

    @Field
    private Date signupDate = new Date();


    public Customer() {
        this.customerType = CustomerType.REGULAR;
    }

    public Customer(String id, String userName, String email, String passsword) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = passsword;
        this.customerType = CustomerType.REGULAR;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumOrders() {
        return numOrders;
    }

    public void setNumOrders(int numOrders) {
        this.numOrders = numOrders;
        this.updateCustomerType();
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    private void updateCustomerType() {
        if (this.numOrders >= 20) {
            this.customerType = CustomerType.PLATINUM;
        } else if (this.numOrders >= 10) {
            this.customerType = CustomerType.GOLD;
        } else {
            this.customerType = CustomerType.REGULAR;
        }
    }

}
