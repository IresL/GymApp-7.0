package com.gym.gymapp.dto;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
