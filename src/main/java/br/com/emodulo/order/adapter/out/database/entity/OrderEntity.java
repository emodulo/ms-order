package br.com.emodulo.order.adapter.out.database.entity;

import br.com.emodulo.order.domain.model.Address;
import br.com.emodulo.order.domain.model.Customer;
import br.com.emodulo.order.domain.model.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_document")
    private String customerDocument;

    @Column(name = "billing_street")
    private String billingStreet;
    @Column(name = "billing_number")
    private String billingNumber;
    @Column(name = "billing_city")
    private String billingCity;
    @Column(name = "billing_state")
    private String billingState;
    @Column(name = "billing_zip")
    private String billingZip;

    @Column(name = "shipping_street")
    private String shippingStreet;
    @Column(name = "shipping_number")
    private String shippingNumber;
    @Column(name = "shipping_city")
    private String shippingCity;
    @Column(name = "shipping_state")
    private String shippingState;
    @Column(name = "shipping_zip")
    private String shippingZip;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEntity> items = new ArrayList<>();
}