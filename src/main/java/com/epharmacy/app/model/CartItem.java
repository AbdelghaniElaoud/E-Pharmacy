package com.epharmacy.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_item")
public class CartItem implements Serializable {
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
    @JoinColumn(name = "cart_id")
    @ToString.Exclude
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    private Product addedProduct;

    public CartItem(Cart cart, Product product, BigDecimal basePrice, BigDecimal discount, Long quantity) {
        this.cart = cart;
        this.addedProduct = product;
        this.basePrice = basePrice;
        this.discount = discount;
        this.quantity = quantity;
        this.totalPrice = basePrice.multiply(BigDecimal.valueOf(quantity));
    }
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}