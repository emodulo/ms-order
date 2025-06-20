package br.com.emodulo.order.adapter.out.database.mapper;

import br.com.emodulo.order.adapter.out.database.entity.OrderEntity;
import br.com.emodulo.order.domain.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderEntityMapper {

    public Order toDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getCustomer(),
                entity.getBillingAddress(),
                entity.getShippingAddress(),
                entity.getItems(),
                entity.getTotal(),
                entity.getCreatedAt());
    }

    public OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setCustomer(order.getCustomer());
        entity.setBillingAddress(order.getBillingAddress());
        entity.setShippingAddress(order.getShippingAddress());
        entity.setItems(order.getItems());
        entity.setTotal(order.getTotal());
        entity.setCreatedAt(order.getCreatedAt());
        return entity;
    }
}

