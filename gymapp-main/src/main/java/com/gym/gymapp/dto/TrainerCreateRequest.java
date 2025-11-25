package com.gym.gymapp.dto;

import lombok.Data;

@Data
public class TrainerCreateRequest {
    private String firstName;
    private String lastName;
    private String specialization;
}
