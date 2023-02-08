package com.interview.interview.controller;


import com.interview.interview.DTO.LoginRequest;
import com.interview.interview.DTO.SignupRequest;
import com.interview.interview.repository.model.Customer;
import com.interview.interview.service.CustomerService;
import com.interview.interview.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public String generateToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }
        return jwtUtil.generateToken(loginRequest.getEmail());

    }

    @PostMapping("/signup") // adding a new customer
    public Customer createUser(@RequestBody SignupRequest loginRequest) throws Exception {
        return customerService.createCustomer(loginRequest);

    }


}
