package com.epharmacy.app.dto.cartitem;

import lombok.Data;

@Data
public class NewCartItemDTO {
    private Long cartId;
    private Long productId;
    private Long quantity;
}
