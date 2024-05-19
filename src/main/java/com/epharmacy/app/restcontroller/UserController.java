package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.response.ResponseDTO;
import com.epharmacy.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('PHARMACIST') or hasRole('DELIVERY_MAN')")
    public ResponseDTO getUserIdByUsername(@PathVariable String username){
        Optional<Long> IDS = userRepository.findUserIdsByUsername(username);
        if ( IDS.isEmpty()){
            return ResponseDTO.builder().ok(false).content("There is no user with this username").build();
        }

        return ResponseDTO.builder().ok(true).content(userRepository.findUserIdsByUsername(username).get()).build();
    }
}
