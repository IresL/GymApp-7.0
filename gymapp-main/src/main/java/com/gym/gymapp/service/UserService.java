package com.gym.gymapp.service;

import com.gym.gymapp.dto.PasswordChangeRequest;
import com.gym.gymapp.dto.UserDto;
import com.gym.gymapp.entity.User;
import com.gym.gymapp.exception.ResourceNotFoundException;
import com.gym.gymapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto changePassword(PasswordChangeRequest request) {
        User user = userRepository.findByUsername(request.getOldPassword())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setActive(user.isActive());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
