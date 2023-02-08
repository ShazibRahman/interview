package com.interview.interview.service;

import com.interview.interview.repository.model.Customer;
import com.interview.interview.repository.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        return customerOptional.map(customer -> new User(customer.getEmail(), customer.getPassword(), new ArrayList<>())).orElse(null);

    }
}
