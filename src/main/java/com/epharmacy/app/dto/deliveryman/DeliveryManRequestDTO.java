package com.epharmacy.app.dto.deliveryman;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryManRequestDTO {
    private String lastName;
    private String firstName;
    private String username;
    private String password;
    private String phone;
    private String email;
}
