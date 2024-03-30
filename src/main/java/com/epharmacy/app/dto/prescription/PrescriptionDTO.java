package com.epharmacy.app.dto.prescription;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
public class PrescriptionDTO {
    private Long id;
    private Date date;
    private String doctor;
    private String link;
}
