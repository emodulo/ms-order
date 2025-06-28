package br.com.emodulo.order.adapter.in.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private CustomerResponseDTO customer;
    private AddressDTO billingAddress;
    private AddressDTO shippingAddress;
    private List<OrderItemDTO> items;
    private BigDecimal total;
    private LocalDateTime createdAt;
}