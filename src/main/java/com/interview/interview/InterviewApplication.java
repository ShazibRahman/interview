package com.interview.interview;

import com.interview.interview.repository.model.Customer;
import com.interview.interview.repository.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class InterviewApplication {

	@Autowired
	private CustomerRepository customerRepository;


	@PostConstruct
	public void init(){
		List<Customer> customers = Stream.of(
				new Customer("1", "John", "john@gmail.com","password"),
				new Customer("2", "Bob", "bob@gmail.com","password")
		).collect(Collectors.toList());
		customerRepository.saveAll(customers);
	}


	public static void main(String[] args) {
		SpringApplication.run(InterviewApplication.class, args);
	}

}
