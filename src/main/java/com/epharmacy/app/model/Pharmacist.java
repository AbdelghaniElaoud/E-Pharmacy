package com.epharmacy.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pharmacist")
@PrimaryKeyJoinColumn(name = "id")
public class Pharmacist extends User implements Serializable {
    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "pharmacist")
    @ToString.Exclude
    private Set<Order> orders;

    public Pharmacist(String username, String email, String password) {
        super(username, email, password);
    }
}