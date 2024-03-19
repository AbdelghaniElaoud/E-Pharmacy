package com.epharmacy.app.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDTO {
    private String code;
    private Double totalPrice;
    private String address;
    private Long customerId;
}
