package com.epharmacy.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.EnableMBeanExport;

import java.io.Serializable;

@Entity
@Setter
@Getter
@ToString
@Table(name = "order_item")
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private Double discount;
    @Column(name = "base_price", nullable = false)
    private Float basePrice;
    @Column(name = "total_price", nullable = false)
    private Float totalPrice;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product_order;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
