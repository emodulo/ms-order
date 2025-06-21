package br.com.emodulo.order.application.usecase;
import br.com.emodulo.order.domain.model.*;
import br.com.emodulo.order.port.out.OrderRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService service;
    private OrderRepositoryPort repository;

    @BeforeEach
    void setup() {
        repository = mock(OrderRepositoryPort.class);
        service = new OrderService(repository);
    }

    @Test
    void shouldCreateOrderAndCalculateTotal() {
        List<Item> items = List.of(
                new Item("1", "Produto A", 2, new BigDecimal("100.00")),
                new Item("2", "Produto B", 1, new BigDecimal("50.00"))
        );
        Order order = new Order(null,
                new Customer("123", "João", "12345678900"),
                new Address("Rua A", "123", "Cidade", "SP", "00000-000"),
                new Address("Rua A", "123", "Cidade", "SP", "00000-000"),
                items,
                null,
                null);

        Order savedOrder = new Order(1L, order.getCustomer(), order.getBillingAddress(), order.getShippingAddress(),
                items, new BigDecimal("250.00"), LocalDateTime.now());

        when(repository.save(any(Order.class))).thenReturn(savedOrder);

        Order result = service.create(order);

        assertNotNull(result.getId());
        assertEquals(new BigDecimal("250.00"), result.getTotal());
        verify(repository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldFindOrderById() {
        Order order = new Order(1L,
                new Customer("123", "João", "12345678900"),
                new Address("Rua A", "123", "Cidade", "SP", "00000-000"),
                new Address("Rua A", "123", "Cidade", "SP", "00000-000"),
                List.of(new Item("1", "Produto", 1, new BigDecimal("100.00"))),
                new BigDecimal("100.00"),
                LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(order));

        Order result = service.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("João", result.getCustomer().getName());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getById(99L));
        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    void shouldReturnAllOrders() {
        List<Order> orders = List.of(
                new Order(1L, new Customer("123", "João", "12345678900"),
                        new Address("Rua A", "123", "Cidade", "SP", "00000-000"),
                        new Address("Rua A", "123", "Cidade", "SP", "00000-000"),
                        List.of(new Item("1", "Produto", 1, new BigDecimal("100.00"))),
                        new BigDecimal("100.00"),
                        LocalDateTime.now())
        );

        when(repository.findAll()).thenReturn(orders);

        List<Order> result = service.listAll();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }
}
