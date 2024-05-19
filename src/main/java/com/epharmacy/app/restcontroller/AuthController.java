package com.epharmacy.app.restcontroller;


import com.epharmacy.app.dto.signupAndSignin.request.LoginRequest;
import com.epharmacy.app.dto.signupAndSignin.request.SignupRequest;
import com.epharmacy.app.dto.signupAndSignin.response.JwtResponse;
import com.epharmacy.app.dto.signupAndSignin.response.MessageResponse;
import com.epharmacy.app.enums.UserRole;
import com.epharmacy.app.model.*;
import com.epharmacy.app.repository.RoleRepository;
import com.epharmacy.app.repository.UserRepository;
import com.epharmacy.app.security.jwt.JwtUtils;
import com.epharmacy.app.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epharmacy.app.security.services.UserDetailsImpl.build;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    Optional<User> user = userRepository.findUserByEmail(loginRequest.getEmail());
    if (user.isEmpty()){
      return ResponseEntity.ok(new JwtResponse(null,null,null,null,null,false, "User not found"));
    } else if (!BCrypt.checkpw(loginRequest.getPassword(),user.get().getPassword())) {
      return ResponseEntity.ok(new JwtResponse(null,null,null,null,null,false, "Invalid password"));

    }

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.get().getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles,true,"Login is successful"));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

     //Create new user's account





    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null || strRoles.isEmpty()) {
      Role userRole = roleRepository.findByName(UserRole.ROLE_CUSTOMER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "customer":
          Role customerRole = roleRepository.findByName(UserRole.ROLE_CUSTOMER)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(customerRole);
          Customer user = new Customer(signUpRequest.getUsername(),
                  signUpRequest.getEmail(),
                  encoder.encode(signUpRequest.getPassword()));

          user.setFirstName(signUpRequest.getFirstName());
          user.setLastName(signUpRequest.getLastName());
          user.setEmail(signUpRequest.getEmail());
          user.setEmail(signUpRequest.getEmail());
          user.setAddress(signUpRequest.getAddress());
          user.setBalance(BigDecimal.valueOf(0));
          user.setPhone(signUpRequest.getPhone());
          user.setRoles(roles);
          userRepository.save(user);
          break;
        case "delivery":
          Role deliveryRole = roleRepository.findByName(UserRole.ROLE_DELIVERY_MAN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(deliveryRole);
          DeliveryMan deliveryMan = new DeliveryMan(signUpRequest.getUsername(),
                  signUpRequest.getEmail(),
                  encoder.encode(signUpRequest.getPassword()));

          deliveryMan.setFirstName(signUpRequest.getFirstName());
          deliveryMan.setLastName(signUpRequest.getLastName());
          deliveryMan.setEmail(signUpRequest.getEmail());
          deliveryMan.setEmail(signUpRequest.getEmail());
          deliveryMan.setPhone(signUpRequest.getPhone());
          deliveryMan.setRoles(roles);
          userRepository.save(deliveryMan);

          break;
        case "admin":
          Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          Admin admin = new Admin(signUpRequest.getUsername(),
                  signUpRequest.getEmail(),
                  encoder.encode(signUpRequest.getPassword()));

          admin.setFirstName(signUpRequest.getFirstName());
          admin.setLastName(signUpRequest.getLastName());
          admin.setEmail(signUpRequest.getEmail());
          admin.setEmail(signUpRequest.getEmail());
          admin.setRoles(roles);
          userRepository.save(admin);

          break;
          case "pharmacist":
          Role pharmacistRole = roleRepository.findByName(UserRole.ROLE_PHARMACIST)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(pharmacistRole);

            Pharmacist pharmacist = new Pharmacist(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));

            pharmacist.setFirstName(signUpRequest.getFirstName());
            pharmacist.setLastName(signUpRequest.getLastName());
            pharmacist.setEmail(signUpRequest.getEmail());
            pharmacist.setEmail(signUpRequest.getEmail());
            pharmacist.setRoles(roles);
            userRepository.save(pharmacist);

          break;
        default:
          Role userRole = roleRepository.findByName(UserRole.ROLE_CUSTOMER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
          Customer user1 = new Customer(signUpRequest.getUsername(),
                  signUpRequest.getEmail(),
                  encoder.encode(signUpRequest.getPassword()));

          user1.setFirstName(signUpRequest.getFirstName());
          user1.setLastName(signUpRequest.getLastName());
          user1.setEmail(signUpRequest.getEmail());
          user1.setEmail(signUpRequest.getEmail());
          user1.setAddress(signUpRequest.getAddress());
          user1.setBalance(BigDecimal.valueOf(0));
          user1.setPhone(signUpRequest.getPhone());
          user1.setRoles(roles);
          userRepository.save(user1);

        }
      });
    }



    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> signOut(){
    return ResponseEntity.ok(new MessageResponse("User signed out successfully!"));
  }
}
