package br.com.emodulo.order.factory;

import br.com.emodulo.order.adapter.in.api.dto.AddressDTO;
import br.com.emodulo.order.adapter.in.api.dto.CustomerDTO;
import br.com.emodulo.order.adapter.in.api.dto.OrderItemDTO;
import br.com.emodulo.order.adapter.in.api.dto.OrderRequestDTO;
import br.com.emodulo.order.adapter.in.api.mapper.OrderDtoMapper;
import br.com.emodulo.order.domain.model.Order;
import org.mockito.Mockito;
import org.springframework.security.oauth2.jwt.Jwt;

import java.math.BigDecimal;
import java.util.List;

public class OrderTestDataFactory {

    public static OrderRequestDTO buildOrderRequestDTO() {
        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setCustomer(new CustomerDTO("1", "Carlos", "88899977766", "1@1.com"));
        dto.setBillingAddress(new AddressDTO("Rua A", "12", "São Paulo", "SP", "01000-000"));
        dto.setShippingAddress(new AddressDTO("Rua B", "99", "São Paulo", "SP", "01001-000"));
        dto.setItems(List.of(new OrderItemDTO("p1", "Produto 1", 2, new BigDecimal("150.00"))));
        dto.setShippingAddressSameAsBilling(false);
        return dto;
    }

    public static Order buildOrderDomain() {

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getSubject()).thenReturn("user-sub-123");

        OrderRequestDTO dto = buildOrderRequestDTO();
        return new OrderDtoMapper().toDomain(dto, jwt);
    }

    public static Order buildOrderDomainWithId() {
        Order order = buildOrderDomain();
        return new Order(1L, order.getCustomer(), order.getBillingAddress(), order.getShippingAddress(), order.getItems(), order.getTotal(), order.getCreatedAt());
    }
}