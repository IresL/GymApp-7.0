package com.gym.gymapp.dto;

import lombok.Data;

@Data
public class TrainingCreateRequest {
    private Long traineeId;
    private Long trainerId;
    private String trainingName;
    private Long trainingTypeId;
    private String trainingDate;
    private int trainingDuration;
}
