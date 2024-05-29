package com.epharmacy.app.service;

import com.epharmacy.app.dto.user.UserDTO1;
import com.epharmacy.app.model.User;
import com.epharmacy.app.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User editProfile(UserDTO1 user) {
        User user1 = userRepository.findById(user.getId()).get();
        user1.setLastName(user.getLastName());
        user1.setFirstName(user.getFirstName());
        user1.setEmail(user.getEmail());

        return  userRepository.save(user1);
    }
}