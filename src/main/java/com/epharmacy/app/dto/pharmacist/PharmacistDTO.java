package com.epharmacy.app.dto.pharmacist;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class PharmacistDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Set<Long> orderIds;
}
