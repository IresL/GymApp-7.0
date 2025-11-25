package com.gym.gymapp.controller;

import com.gym.gymapp.dto.PasswordChangeRequest;
import com.gym.gymapp.dto.UserDto;
import com.gym.gymapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/change-password")
    public ResponseEntity<UserDto> changePassword(@RequestBody PasswordChangeRequest request) {
        return ResponseEntity.ok(userService.changePassword(request));
    }
}
