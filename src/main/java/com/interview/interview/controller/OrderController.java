package com.interview.interview.controller;


import com.interview.interview.DTO.OrderRequest;
import com.interview.interview.repository.model.Order;
import com.interview.interview.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public Order createOrder(@RequestBody OrderRequest request, Principal principal) {
        return orderService.createOrder(request, principal.getName());

    }

}
