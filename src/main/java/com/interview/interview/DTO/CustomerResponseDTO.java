package com.interview.interview.DTO;

import com.interview.interview.enums.CustomerType;

import java.util.Date;

public class CustomerResponseDTO {
    private String id;
    private String userName;
    private String email;
    private CustomerType customerType;

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    private Date signupDate;

    public CustomerResponseDTO() {
    }

    public CustomerResponseDTO(String id, String userName, String email, CustomerType customerType, Date signUpDate) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.customerType = customerType;
        this.signupDate = signUpDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

}
