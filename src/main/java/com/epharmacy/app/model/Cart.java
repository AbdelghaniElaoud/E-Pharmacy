package com.epharmacy.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String code;
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
    private String address;

    @OneToMany(mappedBy = "cart")
    @ToString.Exclude
    private Set<Prescription> prescriptions;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<CartItem> entries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    private Customer customer;

    private boolean active;
}
