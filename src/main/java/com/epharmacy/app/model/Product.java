package com.epharmacy.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Float price;
    @Column(unique = true, nullable = false)
    private String code;
    @Column(name = "require_prescription", nullable = false)
    private boolean prescription;
    @Column(nullable = false)
    private Long stock;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private Set<Image> images;

    @OneToMany(mappedBy = "product_cart")
    @ToString.Exclude
    private Set<CartItem> cartItem;

    @OneToMany(mappedBy = "product_order")
    @ToString.Exclude
    private Set<OrderItem> orderItems;
}