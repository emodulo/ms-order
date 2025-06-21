package br.com.emodulo.order.adapter.in.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private String productId;
    private String name;
    private int quantity;
    private BigDecimal unitPrice;
}
