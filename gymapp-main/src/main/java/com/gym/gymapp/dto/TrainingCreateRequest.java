package com.gym.gymapp.dto;

import lombok.Data;

@Data
public class TrainingCreateRequest {

    private String traineeUsername;
    private String trainerUsername;
    private String trainingDate;     // yyyy-MM-dd
    private Integer durationMinutes; // 60
    private Long trainingTypeId;
    private String trainingName;
}
