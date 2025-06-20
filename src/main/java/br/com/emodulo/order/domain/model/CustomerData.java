package br.com.emodulo.order.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record CustomerData(
        String id,
        String name,
        String document
) {}
