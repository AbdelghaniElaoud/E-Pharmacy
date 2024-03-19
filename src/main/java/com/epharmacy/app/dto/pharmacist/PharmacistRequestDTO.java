package com.epharmacy.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PharmacistRequestDTO {
    private String lastName;
    private String firstName;
    private String username;
    private String password;
    private String phone;
    private String email;
}
