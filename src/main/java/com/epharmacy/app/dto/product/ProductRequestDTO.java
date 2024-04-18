package com.epharmacy.app.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private BigDecimal price;
    private String code;
    private boolean prescription;
    private Long stock;
    private Long categoryId;
}
