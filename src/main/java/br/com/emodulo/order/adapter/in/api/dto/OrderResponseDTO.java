package br.com.emodulo.order.adapter.in.api.dto;

import br.com.emodulo.order.domain.model.AddressData;
import br.com.emodulo.order.domain.model.CustomerData;
import br.com.emodulo.order.domain.model.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        CustomerData customer,
        AddressData billingAddress,
        AddressData shippingAddress,
        List<Item> items,
        BigDecimal total,
        LocalDateTime createdAt
) {}