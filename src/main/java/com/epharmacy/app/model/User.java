package com.epharmacy.app.model;

import com.epharmacy.app.enums.UserRole;
import com.epharmacy.app.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@ToString
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String firstName;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}