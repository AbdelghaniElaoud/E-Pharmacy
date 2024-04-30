package com.epharmacy.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Setter
@Getter
@ToString
@Table(name = "admin")
//@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User implements Serializable {
    public Admin(String username, String email, String password) {
        super(username, email, password);
    }
}
