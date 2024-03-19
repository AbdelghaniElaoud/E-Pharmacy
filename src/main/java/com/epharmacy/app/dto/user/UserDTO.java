package com.epharmacy.app.dto.user;

import com.epharmacy.app.enums.UserRole;
import com.epharmacy.app.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String username;
    private String password;
    private Date createdAt;
    private UserRole role;
    private UserStatus status;
}
