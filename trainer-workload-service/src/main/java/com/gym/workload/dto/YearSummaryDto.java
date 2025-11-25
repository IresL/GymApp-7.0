package com.gym.workload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class YearSummaryDto {
    private int year;
    private java.util.List<MonthSummaryDto> months;
}
