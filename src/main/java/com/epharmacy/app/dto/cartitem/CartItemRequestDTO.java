package com.epharmacy.app.dto.cartitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDTO {
    private Long quantity;
    private Long productId;
    private Long cartId;
    private Long customerId;
}
