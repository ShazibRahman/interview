package com.interview.interview.service;

import com.interview.interview.DTO.OrderRequest;
import com.interview.interview.enums.CustomerType;
import com.interview.interview.repository.model.Customer;
import com.interview.interview.repository.model.Order;
import com.interview.interview.util.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import com.interview.interview.repository.repos.OrderRespository;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Optional;


import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    private OrderService orderService;

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderRespository orderRepository;

    @Mock
    private IdGenerator idGenerator;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService(customerService, idGenerator, orderRepository);
    }

    @Test
    public void createOrder_ValidInput_Success() {
        // Arrange
        OrderRequest orderRequest = new OrderRequest(100.0);
        Customer customer = new Customer("2", "John Doe", "email@test", "password");
        Order order = new Order("order-id", "2", 100.0, 0d, new Date());
        when(customerService.getCustomerByEmail("email@test")).thenReturn(Optional.of(customer));
        when(idGenerator.getOrderId()).thenReturn("order-id");
        when(orderRepository.save(Mockito.any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order createdOrder = orderService.createOrder(orderRequest,"email@test" );

        // Assert
        Assertions.assertNotNull(createdOrder);
        Assertions.assertEquals(createdOrder.getId(), order.getId());
        Assertions.assertEquals(createdOrder.getCustomerId(), order.getCustomerId());
        Assertions.assertEquals(createdOrder.getOrderAmount(), order.getOrderAmount());
        Assertions.assertEquals(createdOrder.getDiscount(), order.getDiscount());
    }

    @Test
    public void createOrder_CustomerNotFound_RumtimeException() {
        // Arrange
        OrderRequest orderRequest;
        orderRequest = new OrderRequest(100.0);
        String customerId = "customer@example.com";
        when(customerService.getCustomerByEmail(customerId)).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(orderRequest, customerId);
        });
        // Act

    }
    @Test
    public void createOrder_Order_Amount_Null() {
        // Arrange
        OrderRequest orderRequest;
        orderRequest = new OrderRequest(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(orderRequest, "");
        });

    }
    @Test
    public void createOrder_Order_Pass_with_Gold_User() {
        Customer customer = new Customer("2", "John Doe", "email@test", "password");
        customer.setNumOrders(10);
        OrderRequest orderRequest;
        orderRequest = new OrderRequest(100.0);
        when(customerService.getCustomerByEmail("email@test")).thenReturn(Optional.of(customer));
        when(idGenerator.getOrderId()).thenReturn("order-id");
        when(orderRepository.save(Mockito.any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Assertions.assertEquals(orderService.createOrder(orderRequest,"email@test" ).getDiscount(), 10.0);

    }
    @Test
    public void createOrder_Order_Pass_with_Platinum_User() {
        Customer customer = new Customer("2", "John Doe", "email@test", "password");
        customer.setNumOrders(20);
        OrderRequest orderRequest;
        orderRequest = new OrderRequest(100.0);
        when(customerService.getCustomerByEmail("email@test")).thenReturn(Optional.of(customer));
        when(idGenerator.getOrderId()).thenReturn("order-id");
        when(orderRepository.save(Mockito.any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Assertions.assertEquals(orderService.createOrder(orderRequest,"email@test" ).getDiscount(), 20.0);

    }
    @Test
    public void createOrder_Order_Pass_with_Regular_User() {
        Customer customer = new Customer("2", "John Doe", "email@test", "password");
        OrderRequest orderRequest;
        orderRequest = new OrderRequest(100.0);
        when(customerService.getCustomerByEmail("email@test")).thenReturn(Optional.of(customer));
        when(idGenerator.getOrderId()).thenReturn("order-id");
        when(orderRepository.save(Mockito.any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Assertions.assertEquals(orderService.createOrder(orderRequest,"email@test" ).getDiscount(), 0.0);

    }
}