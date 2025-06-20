package br.com.emodulo.order.adapter.out.database.entity;

import br.com.emodulo.order.domain.model.AddressData;
import br.com.emodulo.order.domain.model.CustomerData;
import br.com.emodulo.order.domain.model.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Embedded
    private CustomerData customer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
            @AttributeOverride(name = "number", column = @Column(name = "billing_number")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "state", column = @Column(name = "billing_state")),
            @AttributeOverride(name = "zip", column = @Column(name = "billing_zip"))
    })
    private AddressData billingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
            @AttributeOverride(name = "number", column = @Column(name = "shipping_number")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "state", column = @Column(name = "shipping_state")),
            @AttributeOverride(name = "zip", column = @Column(name = "shipping_zip"))
    })
    private AddressData shippingAddress;

    @ElementCollection
    @CollectionTable(name = "tb_order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<Item> items;

    private BigDecimal total;
    private LocalDateTime createdAt;
}
