package br.com.emodulo.order.adapter.out.database.mapper;

import br.com.emodulo.order.adapter.out.database.entity.ItemEntity;
import br.com.emodulo.order.adapter.out.database.entity.OrderEntity;
import br.com.emodulo.order.domain.model.Address;
import br.com.emodulo.order.domain.model.Customer;
import br.com.emodulo.order.domain.model.Item;
import br.com.emodulo.order.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderEntityMapper {
    public OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setCustomerId(order.getCustomer().getId());
        entity.setCustomerName(order.getCustomer().getName());
        entity.setCustomerDocument(order.getCustomer().getDocument());

        entity.setBillingStreet(order.getBillingAddress().getStreet());
        entity.setBillingNumber(order.getBillingAddress().getNumber());
        entity.setBillingCity(order.getBillingAddress().getCity());
        entity.setBillingState(order.getBillingAddress().getState());
        entity.setBillingZip(order.getBillingAddress().getZip());

        entity.setShippingStreet(order.getShippingAddress().getStreet());
        entity.setShippingNumber(order.getShippingAddress().getNumber());
        entity.setShippingCity(order.getShippingAddress().getCity());
        entity.setShippingState(order.getShippingAddress().getState());
        entity.setShippingZip(order.getShippingAddress().getZip());

        entity.setTotal(order.getTotal());
        entity.setCreatedAt(order.getCreatedAt());

        List<ItemEntity> itemEntities = order.getItems().stream().map(i -> {
            ItemEntity e = new ItemEntity();
            e.setProductId(i.getProductId());
            e.setName(i.getName());
            e.setQuantity(i.getQuantity());
            e.setUnitPrice(i.getUnitPrice());
            e.setOrder(entity);
            return e;
        }).toList();

        entity.setItems(itemEntities);
        return entity;
    }

    public Order toDomain(OrderEntity entity) {
        Customer customer = new Customer(entity.getCustomerId(), entity.getCustomerName(), entity.getCustomerDocument());
        Address billing = new Address(entity.getBillingStreet(), entity.getBillingNumber(), entity.getBillingCity(), entity.getBillingState(), entity.getBillingZip());
        Address shipping = new Address(entity.getShippingStreet(), entity.getShippingNumber(), entity.getShippingCity(), entity.getShippingState(), entity.getShippingZip());

        List<Item> items = entity.getItems().stream().map(i ->
                new Item(i.getProductId(), i.getName(), i.getQuantity(), i.getUnitPrice())
        ).toList();

        return new Order(entity.getId(), customer, billing, shipping, items, entity.getTotal(), entity.getCreatedAt());
    }
}


