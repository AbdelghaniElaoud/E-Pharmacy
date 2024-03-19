package com.epharmacy.app.dto.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class CategoryDTO {
    private Long id;
    private String categoryName;
    private Set<Long> productIds;
}
