package com.gym.workload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrainerMonthlySummaryResponse {

    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean trainerStatus;
    private java.util.List<YearSummaryDto> years;
}
