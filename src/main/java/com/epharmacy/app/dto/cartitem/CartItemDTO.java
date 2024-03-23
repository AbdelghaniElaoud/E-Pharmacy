package com.epharmacy.app.dto.cartitem;

import com.epharmacy.app.dto.product.ProductDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CartItemDTO {
    private Long id;
    private Long quantity;
    private BigDecimal discount;
    private BigDecimal basePrice;
    private BigDecimal totalPrice;
    private ProductDTO product;

}
