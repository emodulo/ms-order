package br.com.emodulo.order.adapter.out.database.mapper;

import br.com.emodulo.order.adapter.out.database.entity.*;
import br.com.emodulo.order.domain.model.*;
import br.com.emodulo.order.factory.OrderTestDataFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderEntityMapperTest {

    private final OrderEntityMapper mapper = new OrderEntityMapper();

    @Test
    void shouldMapDomainToEntity() {
        Order order = OrderTestDataFactory.buildOrderDomain();
        OrderEntity entity = mapper.toEntity(order);

        assertNotNull(entity);
        assertEquals("Carlos", entity.getCustomerName());
        assertEquals("SP", entity.getShippingState());
        assertEquals(1, entity.getItems().size());
        assertEquals("Produto 1", entity.getItems().get(0).getName());
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
