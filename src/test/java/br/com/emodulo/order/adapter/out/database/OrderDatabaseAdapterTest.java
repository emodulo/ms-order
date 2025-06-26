package br.com.emodulo.order.adapter.out.database;

import br.com.emodulo.order.adapter.out.database.entity.OrderEntity;
import br.com.emodulo.order.adapter.out.database.mapper.OrderEntityMapper;
import br.com.emodulo.order.adapter.out.database.repository.OrderJpaRepository;
import br.com.emodulo.order.domain.model.*;
import br.com.emodulo.order.factory.OrderTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderDatabaseAdapterTest {

    private OrderJpaRepository jpaRepository;
    private OrderEntityMapper orderMapper;
    private OrderDatabaseAdapter adapter;

    @BeforeEach
    void setup() {
        jpaRepository = mock(OrderJpaRepository.class);
        orderMapper = new OrderEntityMapper();
        adapter = new OrderDatabaseAdapter(jpaRepository, orderMapper);
    }

    @Test
    void shouldSaveOrder() {
        Order order = OrderTestDataFactory.buildOrderDomain();

        OrderEntity entity = orderMapper.toEntity(order);
        entity.setId(1L);

        when(jpaRepository.save(any(OrderEntity.class))).thenReturn(entity);

        Order saved = adapter.save(order);

        assertNotNull(saved.getId());
        assertEquals("Carlos", saved.getCustomer().getName());
    }

    @Test
    void shouldFindOrderById() {
        OrderEntity entity = new OrderEntity();
        entity.setId(1L);
        entity.setCustomerId("1");
        entity.setCustomerName("João");
        entity.setCustomerDocument("11122233344");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setTotal(new BigDecimal("100.00"));

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));

        Order order = adapter.findById(1L).orElse(null);

        assertNotNull(order);
        assertEquals(1L, order.getId());
        assertEquals("João", order.getCustomer().getName());
    }

    @Test
    void shouldReturnEmptyIfOrderNotFound() {
        when(jpaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Order> result = adapter.findById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnAllOrders() {
        OrderEntity entity = new OrderEntity();
        entity.setId(1L);
        entity.setCustomerId("1");
        entity.setCustomerName("Ana");
        entity.setCustomerDocument("00011122233");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setTotal(new BigDecimal("200.00"));

        when(jpaRepository.findAll()).thenReturn(List.of(entity));

        List<Order> result = adapter.findAll();

        assertEquals(1, result.size());
        assertEquals("Ana", result.get(0).getCustomer().getName());
    }
}
