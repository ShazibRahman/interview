package com.interview.interview.repository.repos;

import com.interview.interview.repository.model.Order;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

import java.util.Optional;

public interface OrderRespository extends CouchbasePagingAndSortingRepository<Order,String> {
    @Override
    Optional<Order> findById(String s);
}
