package com.interview.interview.service;


import com.interview.interview.DTO.OrderRequest;
import com.interview.interview.enums.CustomerType;
import com.interview.interview.repository.model.Customer;
import com.interview.interview.repository.model.Order;
import com.interview.interview.repository.repos.OrderRespository;
import com.interview.interview.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class OrderService {

    private final CustomerService customerService;


    private final IdGenerator idGenerator;

    public OrderService(CustomerService customerService, IdGenerator idGenerator, OrderRespository orderRespository) {
        this.customerService = customerService;
        this.idGenerator = idGenerator;
        this.orderRespository = orderRespository;
    }

    private final OrderRespository orderRespository;

    public Order createOrder(OrderRequest orderRequest, String emailId) {
        validateOrderRequest(orderRequest);
        Order order = new Order();
        order.setOrderAmount(orderRequest.getOrderAmount());



        Optional<Customer> customerOptional = customerService.getCustomerByEmail(emailId);

        if (!customerOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"); // customer not found
        }
        Customer customer = customerOptional.get();
        order.setCustomerId(customer.getId());
        customerService.increaseOrderCount(emailId); // increase order count
        if (customer.getCustomerType()== CustomerType.GOLD) {
            order.setDiscount(order.getOrderAmount() * 0.1);
        } else if (customer.getCustomerType()== CustomerType.PLATINUM) {
            order.setDiscount(order.getOrderAmount() * 0.2);
        }
        String id = idGenerator.getOrderId(); //orderId generator
        order.setId(id);
        return orderRespository.save(order);
    }

    private void validateOrderRequest(OrderRequest orderRequest) {
        if (orderRequest.getOrderAmount() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Amount is required");
        }
    }

}