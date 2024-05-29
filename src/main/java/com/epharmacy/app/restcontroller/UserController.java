package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.response.ResponseDTO;
import com.epharmacy.app.dto.user.UserDTO;
import com.epharmacy.app.dto.user.UserDTO1;
import com.epharmacy.app.mappers.UserMapper;
import com.epharmacy.app.model.User;
import com.epharmacy.app.repository.UserRepository;
import com.epharmacy.app.service.MediaService;
import com.epharmacy.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final MediaService mediaService;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, MediaService mediaService, UserService userService) {
        this.userRepository = userRepository;
        this.mediaService = mediaService;
        this.userService = userService;
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

    @PostMapping("/{userId}/add-profile")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('PHARMACIST') or hasRole('DELIVERY_MAN')")
    public ResponseDTO addProfile(@PathVariable Long userId, @RequestParam("image") MultipartFile photo) throws IOException {

        return ResponseDTO.builder().ok(true).content(UserMapper.toUserDTO(mediaService.addProfile(userId,photo))).build();
    }

    @GetMapping("/{userId}/get-profile")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('PHARMACIST') or hasRole('DELIVERY_MAN')")
    public ResponseDTO getProfile(@PathVariable Long userId) throws IOException {

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            return ResponseDTO.builder().ok(true).content(UserMapper.toUserDTO(user.get())).build();
        }
        return ResponseDTO.builder().ok(false).content("There is no user with this id : "+userId ).build();
    }

    @PostMapping("/update-profile")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('PHARMACIST') or hasRole('DELIVERY_MAN')")
    public ResponseDTO updateProfile(UserDTO1 user){
        return ResponseDTO.builder().ok(true).content(UserMapper.toUserDTO(userService.editProfile(user))).build();
    }

    @GetMapping("/non-admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDTO getAllNonAdminUsers() {
        List<User> users = userService.getAllNonAdminUsers();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users){
            userDTOS.add(UserMapper.toUserDTO(user));
        }
        return ResponseDTO.builder().ok(true).content(userDTOS).build();
    }
}
