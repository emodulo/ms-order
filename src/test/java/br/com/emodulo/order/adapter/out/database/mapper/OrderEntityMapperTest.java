package br.com.emodulo.order.adapter.out.database.mapper;

import br.com.emodulo.order.adapter.out.database.entity.*;
import br.com.emodulo.order.domain.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderEntityMapperTest {

    private final OrderEntityMapper mapper = new OrderEntityMapper();

    @Test
    void shouldMapDomainToEntity() {
        Customer customer = new Customer("1", "Ana", "12345678900");
        Address billing = new Address("Rua X", "1", "SP", "SP", "01000-000");
        Address shipping = new Address("Rua Y", "2", "SP", "SP", "01001-000");
        List<Item> items = List.of(new Item("v1", "Carro", 1, new BigDecimal("100000")));

        Order order = new Order(1L, customer, billing, shipping, items, new BigDecimal("100000"), LocalDateTime.now());

        OrderEntity entity = mapper.toEntity(order);

        assertNotNull(entity);
        assertEquals("Ana", entity.getCustomerName());
        assertEquals("SP", entity.getShippingState());
        assertEquals(1, entity.getItems().size());
        assertEquals("Carro", entity.getItems().get(0).getName());
    }

    @Test
    void shouldMapEntityToDomain() {
        OrderEntity entity = new OrderEntity();
        entity.setId(2L);
        entity.setCustomerId("c2");
        entity.setCustomerName("Bruno");
        entity.setCustomerDocument("9988776655");
        entity.setBillingStreet("Av. Central");
        entity.setBillingNumber("100");
        entity.setBillingCity("RJ");
        entity.setBillingState("RJ");
        entity.setBillingZip("20000-000");
        entity.setShippingStreet("Rua das Flores");
        entity.setShippingNumber("50");
        entity.setShippingCity("RJ");
        entity.setShippingState("RJ");
        entity.setShippingZip("20001-000");
        entity.setTotal(new BigDecimal("150.00"));
        entity.setCreatedAt(LocalDateTime.now());

        ItemEntity item = new ItemEntity();
        item.setProductId("p1");
        item.setName("Produto");
        item.setQuantity(1);
        item.setUnitPrice(new BigDecimal("150.00"));
        item.setOrder(entity);

        entity.setItems(List.of(item));

        Order domain = mapper.toDomain(entity);

        assertEquals("Bruno", domain.getCustomer().getName());
        assertEquals("Produto", domain.getItems().get(0).getName());
        assertEquals(new BigDecimal("150.00"), domain.getTotal());
    }
}
