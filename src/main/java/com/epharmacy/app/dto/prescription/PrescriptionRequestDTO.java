package com.epharmacy.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionRequestDTO {
    private String date;
    private String doctor;
    private Long customerId;
    private Long cartId;
    private Long orderId;
}
