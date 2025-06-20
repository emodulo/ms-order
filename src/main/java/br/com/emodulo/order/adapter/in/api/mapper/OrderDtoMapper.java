package br.com.emodulo.order.adapter.in.api.mapper;

import br.com.emodulo.order.adapter.in.api.dto.OrderRequestDTO;
import br.com.emodulo.order.adapter.in.api.dto.OrderResponseDTO;
import br.com.emodulo.order.domain.model.AddressData;
import br.com.emodulo.order.domain.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class OrderDtoMapper {

    public Order toDomain(OrderRequestDTO dto) {
        AddressData shipping = dto.shippingAddressSameAsBilling() ? dto.billingAddress() : dto.shippingAddress();

        BigDecimal total = dto.items().stream()
                .map(i -> i.unitPrice().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Order(null, dto.customer(), dto.billingAddress(), shipping,
                dto.items(), total, LocalDateTime.now());
    }

    public OrderResponseDTO toResponseDTO(Order domain) {
        return new OrderResponseDTO(domain.getId(), domain.getCustomer(), domain.getBillingAddress(),
                domain.getShippingAddress(), domain.getItems(), domain.getTotal(), domain.getCreatedAt());
    }
}

