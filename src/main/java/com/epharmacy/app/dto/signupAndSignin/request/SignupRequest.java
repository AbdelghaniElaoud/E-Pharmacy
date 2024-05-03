package com.epharmacy.app.dto.signupAndSignin.request;

import com.epharmacy.app.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
  @Getter
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @Getter
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @Getter
  private Set<String> role;

  @Getter
  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  @NotBlank
  private String lastName;
  @NotBlank
  private String firstName;
  @NotBlank
  private String phone;
  @NotBlank
  private String address;

  // Constructor
  public SignupRequest(String username, String email, String password, String lastName, String firstName, String phone, String address, Set<String> roles) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.lastName = lastName;
    this.firstName = firstName;
    this.phone = phone;
    this.address = address;
    this.role = roles;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }
}
