package br.com.emodulo.order.adapter.in.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private CustomerDTO customer;
    private AddressDTO billingAddress;
    private AddressDTO shippingAddress;
    private List<OrderItemDTO> items;
    private boolean shippingAddressSameAsBilling;
}