package com.epharmacy.app.dto.orderitem;

import com.epharmacy.app.dto.product.ProductDTO;
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
    private ProductDTO product;
    private Long orderId;
}
