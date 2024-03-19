package com.epharmacy.app.dto.orderItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDTO {
    private int quantity;
    private Double discount;
    private Float basePrice;
    private Float totalPrice;
    private Long productId;
    private Long orderId;
}
