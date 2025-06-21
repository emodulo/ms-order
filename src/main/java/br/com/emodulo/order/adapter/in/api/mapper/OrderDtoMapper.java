package br.com.emodulo.order.adapter.in.api.mapper;

import br.com.emodulo.order.adapter.in.api.dto.*;
import br.com.emodulo.order.domain.model.Address;
import br.com.emodulo.order.domain.model.Customer;
import br.com.emodulo.order.domain.model.Item;
import br.com.emodulo.order.domain.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderDtoMapper {
    public Order toDomain(OrderRequestDTO dto) {
        Customer customer = new Customer(dto.getCustomer().getId(), dto.getCustomer().getName(), dto.getCustomer().getDocument());
        Address billing = new Address(
                dto.getBillingAddress().getStreet(),
                dto.getBillingAddress().getNumber(),
                dto.getBillingAddress().getCity(),
                dto.getBillingAddress().getState(),
                dto.getBillingAddress().getZip()
        );

        Address shipping = dto.isShippingAddressSameAsBilling()
                ? billing
                : new Address(
                dto.getShippingAddress().getStreet(),
                dto.getShippingAddress().getNumber(),
                dto.getShippingAddress().getCity(),
                dto.getShippingAddress().getState(),
                dto.getShippingAddress().getZip()
        );

        List<Item> items = dto.getItems().stream()
                .map(i -> new Item(i.getProductId(), i.getName(), i.getQuantity(), i.getUnitPrice()))
                .toList();

        BigDecimal total = items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Order(null, customer, billing, shipping, items, total, LocalDateTime.now());
    }

    public OrderResponseDTO toResponseDTO(Order order) {
        CustomerDTO customer = new CustomerDTO(order.getCustomer().getId(), order.getCustomer().getName(), order.getCustomer().getDocument());
        AddressDTO billing = new AddressDTO(
                order.getBillingAddress().getStreet(),
                order.getBillingAddress().getNumber(),
                order.getBillingAddress().getCity(),
                order.getBillingAddress().getState(),
                order.getBillingAddress().getZip()
        );

        AddressDTO shipping = new AddressDTO(
                order.getShippingAddress().getStreet(),
                order.getShippingAddress().getNumber(),
                order.getShippingAddress().getCity(),
                order.getShippingAddress().getState(),
                order.getShippingAddress().getZip()
        );

        List<OrderItemDTO> items = order.getItems().stream()
                .map(i -> new OrderItemDTO(i.getProductId(), i.getName(), i.getQuantity(), i.getUnitPrice()))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                customer,
                billing,
                shipping,
                items,
                order.getTotal(),
                order.getCreatedAt()
        );
    }
}