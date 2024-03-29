package com.epharmacy.app.dto.review;

import com.epharmacy.app.model.ReviewStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {
    private Long id;
    private String label;
    private Integer rate;
    private Long customerId;
    private Long deliveryManId;
    private Long orderId;
    private ReviewStatus status;
}
