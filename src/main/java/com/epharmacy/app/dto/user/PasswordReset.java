package com.epharmacy.app.dto.user;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class PasswordReset {
    private String newPassword;
    private LocalTime time;
}
