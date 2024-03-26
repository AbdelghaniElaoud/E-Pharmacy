package com.epharmacy.app.dto.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseReviewDTO {
    private Integer id;
    private String label;
    private Integer rate;
    private String customer;
}
