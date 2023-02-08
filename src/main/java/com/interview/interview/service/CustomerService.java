package com.interview.interview.service;


import com.interview.interview.DTO.SignupRequest;
import com.interview.interview.repository.model.Customer;
import com.interview.interview.repository.repos.CustomerRepository;
import com.interview.interview.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final IdGenerator idGenerator;

    public CustomerService(CustomerRepository customerRepository, IdGenerator idGenerator) {
        this.customerRepository = customerRepository;
        this.idGenerator = idGenerator;
    }

    public Optional<Customer> getCustomerById(String customerId) {
            return customerRepository.findById(customerId);
        }
        public Optional<Customer> getCustomerByEmail(String email) {
            return customerRepository.findByEmail(email);
        }

    private void validateRequest(SignupRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if(request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (getCustomerByEmail(request.getEmail()).isPresent()) { // if the email already exists
            throw new IllegalArgumentException("Email already exists");
        }
    }
        public Customer createCustomer(SignupRequest request) {
            validateRequest(request);
            Customer customer = new Customer();
            customer.setId(idGenerator.getCustomerId());
            customer.setEmail(request.getEmail());
            customer.setUserName(request.getName());
            customer.setPassword(request.getPassword());
            return customerRepository.save(customer);
        }



    public void increaseOrderCount(String customerId) {
        Optional<Customer> customerOptional = getCustomerByEmail(customerId);
        if (!customerOptional.isPresent()) {
            throw new IllegalArgumentException("Customer not found");
        }
        Customer customer = customerOptional.get();
        customer.setNumOrders(customer.getNumOrders() + 1);
        customerRepository.save(customer);
    }
}
