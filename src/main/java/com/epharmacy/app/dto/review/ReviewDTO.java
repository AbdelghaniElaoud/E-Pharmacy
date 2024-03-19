package com.epharmacy.app.dto.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDTO {
    private Integer id;
    private String label;
    private Integer rate;
    private Long customerId;
    private Long deliveryManId;
    private Long orderId;
}
