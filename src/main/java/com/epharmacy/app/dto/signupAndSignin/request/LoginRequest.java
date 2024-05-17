package com.epharmacy.app.dto.signupAndSignin.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
