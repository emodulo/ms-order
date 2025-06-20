package br.com.emodulo.order.domain.model;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record Item(
        String productId,
        String name,
        int quantity,
        BigDecimal unitPrice
) {}
