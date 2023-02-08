package com.interview.interview.controller;


import com.interview.interview.DTO.CustomerResponseDTO;
import com.interview.interview.DTO.LoginRequest;
import com.interview.interview.DTO.SignupRequest;
import com.interview.interview.repository.model.Customer;
import com.interview.interview.service.CustomerService;
import com.interview.interview.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
            Optional<Customer> optionalCustomer =  customerService.getCustomerByEmail(loginRequest.getEmail());
            if (!optionalCustomer.isPresent()) {
                throw new Exception("Invalid username/password");
            }
            Customer customer = optionalCustomer.get();
             if ( !BCrypt.checkpw(loginRequest.getPassword(), customer.getPassword()))
                    throw new Exception("Invalid username/password");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),customer.getPassword()));
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }
        return jwtUtil.generateToken(loginRequest.getEmail());

    }

    @PostMapping("/signup") // adding a new customer
    public CustomerResponseDTO createUser(@RequestBody SignupRequest loginRequest) throws Exception {
        String enccryptedPassword = BCrypt.hashpw(loginRequest.getPassword(), BCrypt.gensalt());
        loginRequest.setPassword(enccryptedPassword);
        return customerService.createCustomer(loginRequest);

    }


}
