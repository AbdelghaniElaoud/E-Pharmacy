package com.epharmacy.app.dto.cartItem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartItemDTO {
    private Long id;
    private int quantity;
    private Double discount;
    private Float basePrice;
    private Float totalPrice;
    private Long cartId;
    private Long productId;


}
