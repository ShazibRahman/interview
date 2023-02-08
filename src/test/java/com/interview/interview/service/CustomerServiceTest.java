package com.interview.interview.service;

import com.interview.interview.DTO.CustomerResponseDTO;
import com.interview.interview.DTO.SignupRequest;
import com.interview.interview.repository.model.Customer;
import com.interview.interview.repository.repos.CustomerRepository;
import com.interview.interview.util.IdGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {


    @Mock
    private CustomerRepository customerRepository ;

    @Mock
    private IdGenerator idGenerator;

    @Test
    public void createCustomer_withValidInput_createsCustomerSuccessfully() {
        CustomerService customerService = new CustomerService(customerRepository, idGenerator);
        Mockito.when(customerRepository.findByEmail("test@email.com")).thenReturn(Optional.empty());
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SignupRequest request = new SignupRequest("test@email.com", "test@email.com", "testPassword");
        Customer expectedCustomer = new Customer("generatedId", "test@email.com", "test@email.com", "testPassword");


        CustomerResponseDTO actualCustomer = customerService.createCustomer(request);

        Assertions.assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
    }

    @Test
    public void createCustomer_withEmptyEmail_throwsIllegalArgumentException() {
        CustomerService customerService = new CustomerService(customerRepository, idGenerator);

        SignupRequest request = new SignupRequest("", "testName", "testPassword");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(request);
        });
    }

    @Test
    public void createCustomer_withEmptyPassword_throwsIllegalArgumentException() {
        CustomerService customerService = new CustomerService(customerRepository, idGenerator);

        SignupRequest request = new SignupRequest("test@email.com", "testName", "");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(request);
        });
    }

    @Test
    public void createCustomer_withEmptyName_throwsIllegalArgumentException() {
        CustomerService customerService = new CustomerService(customerRepository, idGenerator);

        SignupRequest request = new SignupRequest("test@email.com", "", "testPassword");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(request);
        });
    }

    @Test
    public void createCustomer_withAlreadyExistingEmail_throwsIllegalArgumentException() {
        CustomerService customerService = new CustomerService(customerRepository, idGenerator);
        Mockito.when(customerRepository.findByEmail("test@email.com")).thenReturn(Optional.of(new Customer()));


        SignupRequest request = new SignupRequest("test@email.com", "test@email.com", "testPassword");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(request);
        });
    }

}
