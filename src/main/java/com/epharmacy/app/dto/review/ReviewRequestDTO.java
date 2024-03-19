package com.epharmacy.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {
    private String label;
    private Integer rate;
    private Long customerId;
    private Long deliveryManId;
    private Long orderId;
}
