package com.gym.gymapp.dto;

import lombok.Data;

@Data
public class TrainingDto {
    private Long id;
    private String traineeUsername;
    private String trainerUsername;
    private String trainingName;
    private String trainingType;
    private String trainingDate;
    private int trainingDuration;
}
