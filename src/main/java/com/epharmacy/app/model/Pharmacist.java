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
@Table(name = "pharmacist")
@PrimaryKeyJoinColumn(name = "id")
public class Pharmacist extends User implements Serializable {
    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "pharmacist")
    @ToString.Exclude
    private Set<Order> orders;
}