package com.epharmacy.app.dto.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class ProductDTO {
    private Long id;
    private String name;
    private Float price;
    private String code;
    private boolean prescription;
    private Long stock;
    private Long categoryId;
    private Set<Long> imageIds;
}
