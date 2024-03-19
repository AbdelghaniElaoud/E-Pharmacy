package com.epharmacy.app.dto.user;

import com.epharmacy.app.enums.UserRole;
import com.epharmacy.app.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
public class UserRequestDTO {
    @NotBlank
    private String lastName;

    @NotBlank
    private String firstName;

    @NotBlank
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must contain only letters, numbers, and underscores")
    private String username;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private Date createdAt;

    @NotNull
    private UserRole role;

    @NotNull
    private UserStatus status;

}
