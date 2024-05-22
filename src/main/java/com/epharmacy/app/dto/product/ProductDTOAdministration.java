package com.epharmacy.app.dto.product;

import com.epharmacy.app.dto.category.CategoryDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductDTOAdministration {
    private Long id;
    private String name;
    private BigDecimal price;
    private String code;
    private boolean prescription;
    private Long stock;
    private CategoryDTO category;
    private boolean active;
}
