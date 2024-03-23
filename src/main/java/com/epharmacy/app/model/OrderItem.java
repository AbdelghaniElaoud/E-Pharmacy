package com.epharmacy.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "order_item")
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long quantity;
    @Column(nullable = false)
    private BigDecimal discount;
    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product productOrder;

    public OrderItem(Long quantity, BigDecimal discount, BigDecimal basePrice, BigDecimal totalPrice, Product productOrder) {
        this.quantity = quantity;
        this.discount = discount;
        this.basePrice = basePrice;
        this.totalPrice = totalPrice;
        this.productOrder = productOrder;
    }
}
