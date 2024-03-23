package com.epharmacy.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "delivery_man")
@PrimaryKeyJoinColumn(name = "id")
public class DeliveryMan extends User implements Serializable {
    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "deliveryMan")
    @ToString.Exclude
    private Set<Order> orders;

    @OneToMany(mappedBy = "deliveryMan")
    @ToString.Exclude
    private List<Review> reviews ;

}