package com.gym.gymapp.dto;

import lombok.Data;

@Data
public class TraineeCreateRequest {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
}
