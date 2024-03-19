package com.epharmacy.app.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private Float price;
    private String code;
    private boolean prescription;
    private Long stock;
    private Long categoryId;
}
