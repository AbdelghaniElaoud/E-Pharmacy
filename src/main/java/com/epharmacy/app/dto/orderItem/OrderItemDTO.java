package com.epharmacy.app.dto.orderItem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItemDTO {
    private Long id;
    private int quantity;
    private Double discount;
    private Float basePrice;
    private Float totalPrice;
    private Long productId;
    private Long orderId;
}
