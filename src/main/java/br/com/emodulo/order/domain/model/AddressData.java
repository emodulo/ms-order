package br.com.emodulo.order.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record AddressData(
        String street,
        String number,
        String city,
        String state,
        String zip
) {}