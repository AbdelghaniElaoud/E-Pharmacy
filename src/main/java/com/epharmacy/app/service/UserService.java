package com.epharmacy.app.service;

import com.epharmacy.app.dto.user.PasswordReset;
import com.epharmacy.app.dto.user.PasswordResetRequest;
import com.epharmacy.app.dto.user.UserDTO1;
import com.epharmacy.app.enums.UserStatus;
import com.epharmacy.app.model.User;
import com.epharmacy.app.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    EmailContentBuilder emailContentBuilder;

    private final EmailSenderService emailSenderService;

    public UserService(UserRepository userRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    public User editProfile(UserDTO1 user) {
        User user1 = userRepository.findById(user.getId()).get();
        user1.setLastName(user.getLastName());
        user1.setFirstName(user.getFirstName());
        user1.setEmail(user.getEmail());

        return  userRepository.save(user1);
    }

    public List<User> getAllNonAdminUsers() {
        return userRepository.findAllNonAdminUsers();
    }

    public void activateOrDeactivate(Long id) {
        User user = userRepository.findById(id).get();

        if (user.getStatus() == UserStatus.ACTIVE ){
            user.setStatus(UserStatus.INACTIVE);
        }else {
            user.setStatus(UserStatus.ACTIVE);
        }
        userRepository.save(user);
    }

    public String updatePasswordRequest(PasswordResetRequest request) throws MessagingException {
        String email = request.getEmail();
        Optional<User> user = userRepository.findUserByEmail(email);

        if (user.isEmpty()) {
            return "Account not found";
        }

        String resetLink = "http://localhost:8080/reset-password?userId=" + user.get().getId() + "&timestamp=" + LocalTime.now().toString();
        String emailContent = emailContentBuilder.buildPasswordResetEmail(resetLink);

        emailSenderService.sendHtmlEmail(request.getEmail(), "Password Reset Request", emailContent);

        return "The email is in your inbox";
    }

    public String updatePassword(Long userId, PasswordReset request) {
        User userOptional = userRepository.findById(userId).get();
        if (Duration.between(request.getTime(), LocalTime.now()).toMinutes() > 5){
            return "The link is expired";
        }

        userOptional.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(userOptional);

        return "The password has been updated successfully";
    }
}
