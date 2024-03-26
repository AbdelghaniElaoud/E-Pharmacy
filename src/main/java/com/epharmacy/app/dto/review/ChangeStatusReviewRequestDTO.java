package com.epharmacy.app.dto.review;

import com.epharmacy.app.model.ReviewStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeStatusReviewRequestDTO {
    private Long id;
    private ReviewStatus status;
}
