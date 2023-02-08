package com.interview.interview.repository.repos;

import com.interview.interview.repository.model.Customer;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface CustomerRepository extends CouchbasePagingAndSortingRepository<Customer, String> {

    @Override
    Optional<Customer> findById(String s);
    Optional<Customer> findByEmail(String email);
}
