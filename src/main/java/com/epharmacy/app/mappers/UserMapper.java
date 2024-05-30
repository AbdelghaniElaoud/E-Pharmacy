package com.epharmacy.app.mappers;

import com.epharmacy.app.dto.image.MediaDTO;
import com.epharmacy.app.dto.user.UserDTO;
import com.epharmacy.app.enums.UserRole;
import com.epharmacy.app.model.Media;
import com.epharmacy.app.model.Role;
import com.epharmacy.app.model.User;

import java.util.Iterator;
import java.util.Set;

public class UserMapper {

    public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        Set<Role> roles = user.getRoles();
        Iterator<Role> iterator = roles.iterator();
        String role = "";

        if (iterator.hasNext()) {
            UserRole name = iterator.next().getName();
            if (name == UserRole.ROLE_CUSTOMER) {
                role = "customer";
            } else if (name == UserRole.ROLE_ADMIN) {
                role = "admin";
            } else if (name == UserRole.ROLE_PHARMACIST) {
                role = "pharmacist";
            } else if (name == UserRole.ROLE_DELIVERY_MAN) {
                role = "delivery";
            }
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLastName(user.getLastName());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(role);
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(java.sql.Date.valueOf(user.getCreatedAt().toLocalDate()));
        userDTO.setStatus(user.getStatus());

        if (user.getProfilePhoto() != null) {
            MediaDTO mediaDTO = new MediaDTO();
            mediaDTO.setId(user.getProfilePhoto().getId());
            mediaDTO.setAltText(user.getProfilePhoto().getAltText());
            mediaDTO.setLink(user.getProfilePhoto().getLink());
            userDTO.setProfilePhoto(mediaDTO);
        }

        return userDTO;
    }


    public static User toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setUsername(userDTO.getUsername());
        user.setCreatedAt(userDTO.getCreatedAt().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        user.setStatus(userDTO.getStatus());

        if (userDTO.getProfilePhoto() != null) {
            Media media = new Media();
            media.setId(userDTO.getProfilePhoto().getId());
            media.setAltText(userDTO.getProfilePhoto().getAltText());
            media.setLink(userDTO.getProfilePhoto().getLink());
            user.setProfilePhoto(media);
        }



        return user;
    }
}
