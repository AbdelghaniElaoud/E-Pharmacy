package com.epharmacy.app.dto.prescription;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PrescriptionDTO {
    private Long id;
    private LocalDateTime date;
    private String doctor;
    private Long customerId;
    private Long cartId;
    private Long imageId;
    private Long orderId;
}
