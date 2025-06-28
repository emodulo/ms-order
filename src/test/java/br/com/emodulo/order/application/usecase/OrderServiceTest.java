package br.com.emodulo.order.application.usecase;
import br.com.emodulo.order.application.usecase.config.OrderProperties;
import br.com.emodulo.order.domain.model.*;
import br.com.emodulo.order.factory.OrderTestDataFactory;
import br.com.emodulo.order.port.out.InventoryClientPort;
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
    private InventoryClientPort inventory;
    private OrderProperties orderProperties;

    @BeforeEach
    void setup() {
        repository = mock(OrderRepositoryPort.class);
        inventory = mock(InventoryClientPort.class);
        orderProperties = mock(OrderProperties.class);
        service = new OrderService(repository, inventory,orderProperties);
    }

    @Test
    void shouldCreateOrderAndCalculateTotal() {
        List<Item> items = List.of(
                new Item("1", "Produto A", 2, new BigDecimal("100.00")),
                new Item("2", "Produto B", 1, new BigDecimal("50.00"))
        );
        Order order = OrderTestDataFactory.buildOrderDomain();

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
        Order order = OrderTestDataFactory.buildOrderDomain();

        when(repository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = service.getById(1L);

        assertEquals(1L, result.get().getId());
        assertEquals("João", result.get().getCustomer().getName());
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
                OrderTestDataFactory.buildOrderDomain()
        );

        when(repository.findAll()).thenReturn(orders);

        List<Order> result = service.listAll();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldCallDecreaseStock_whenEnabled() {
        Item item = new Item("p1", "Produto 1", 2, new BigDecimal("100.00"));
        Customer customer = new Customer("1", "ABC", "João", "12345678900", "1@1.com.br");
        Address address = new Address("Rua A", "10", "SP", "SP", "01000-000");
        Order order = new Order(null, customer, address, address, List.of(item), new BigDecimal("200.00"), LocalDateTime.now());

        when(repository.save(any())).thenReturn(order);
        when(orderProperties.isDecreaseStockEnabled()).thenReturn(true);

        Order result = service.create(order);

        assertNotNull(result);
        verify(inventory, times(1)).decreaseStock("p1", 2);
    }

    @Test
    void shouldNotCallDecreaseStock_whenDisabled() {
        Item item = new Item("p1", "Produto 1", 2, new BigDecimal("100.00"));
        Customer customer = new Customer("1", "ABC", "João", "12345678900", "1@1.com.br");
        Address address = new Address("Rua A", "10", "SP", "SP", "01000-000");
        Order order = new Order(null, customer, address, address, List.of(item), new BigDecimal("200.00"), LocalDateTime.now());

        when(repository.save(any())).thenReturn(order);
        when(orderProperties.isDecreaseStockEnabled()).thenReturn(false);

        Order result = service.create(order);


        assertNotNull(result);
        verify(inventory, never()).decreaseStock(any(), anyInt());
    }
}
