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
@Table(name = "cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String code;
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;
    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "cart")
    @ToString.Exclude
    private Set<Prescription> prescriptions;

    @OneToMany(mappedBy = "cart")
    @ToString.Exclude
    private Set<CartItem> cartItems;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    private Customer customer;
}
