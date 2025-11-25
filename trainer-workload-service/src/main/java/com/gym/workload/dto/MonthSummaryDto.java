package com.gym.workload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthSummaryDto {
    private int month;
    private int trainingSummaryDuration;
}
