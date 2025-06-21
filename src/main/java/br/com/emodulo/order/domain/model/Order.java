package br.com.emodulo.order.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Order {
    private final Long id;
    private final Customer customer;
    private final Address billingAddress;
    private final Address shippingAddress;
    private final List<Item> items;
    private final BigDecimal total;
    private final LocalDateTime createdAt;
}

