package com.gym.gymapp.integration;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainerWorkloadRequest {

    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean active;
    private LocalDate trainingDate;
    private int trainingDuration;
    private ActionType actionType;

    public enum ActionType {
        ADD,
        DELETE
    }
}
