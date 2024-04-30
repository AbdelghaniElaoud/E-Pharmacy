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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User implements Serializable {
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private Set<Prescription> prescription;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private List<Cart> carts;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private Set<Order> orders;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private List<Review> reviews ;

    public Customer(String username, String email, String password) {
        super(username, email, password);
    }
}
