package com.epharmacy.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDTO {
    private String lastName;
    private String firstName;
    private String username;
    private String password;
    private String phone;
    private String address;
    private String email;
}
