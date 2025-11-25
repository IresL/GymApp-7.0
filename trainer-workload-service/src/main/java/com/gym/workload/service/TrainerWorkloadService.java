package com.gym.workload.service;

import com.gym.workload.dto.MonthSummaryDto;
import com.gym.workload.dto.TrainerMonthlySummaryResponse;
import com.gym.workload.dto.TrainerWorkloadRequest;
import com.gym.workload.dto.YearSummaryDto;
import com.gym.workload.entity.TrainerMonthlyWorkload;
import com.gym.workload.repository.TrainerMonthlyWorkloadRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerWorkloadService {

    private static final Logger log = LoggerFactory.getLogger(TrainerWorkloadService.class);

    private final TrainerMonthlyWorkloadRepository repository;

    @Transactional
    public void applyWorkload(TrainerWorkloadRequest request) {
        log.info("Applying workload: trainer={}, date={}, duration={}, action={}",
                request.getTrainerUsername(),
                request.getTrainingDate(),
                request.getTrainingDuration(),
                request.getActionType());

        LocalDate date = request.getTrainingDate();
        int year = date.getYear();
        int month = date.getMonthValue();

        TrainerMonthlyWorkload wm = repository
                .findByTrainerUsernameAndYearAndMonth(request.getTrainerUsername(), year, month)
                .orElseGet(() -> {
                    TrainerMonthlyWorkload n = new TrainerMonthlyWorkload();
                    n.setTrainerUsername(request.getTrainerUsername());
                    n.setTrainerFirstName(request.getTrainerFirstName());
                    n.setTrainerLastName(request.getTrainerLastName());
                    n.setActive(request.isActive());
                    n.setYear(year);
                    n.setMonth(month);
                    n.setTotalDuration(0);
                    return n;
                });

        int delta = request.getTrainingDuration();
        if (request.getActionType() == TrainerWorkloadRequest.ActionType.DELETE) {
            delta = -delta;
        }

        int newTotal = wm.getTotalDuration() + delta;
        if (newTotal < 0) {
            newTotal = 0;
        }
        wm.setTotalDuration(newTotal);
        wm.setActive(request.isActive());

        repository.save(wm);
        log.info("Workload updated: trainer={}, year={}, month={}, totalMinutes={}",
                wm.getTrainerUsername(), wm.getYear(), wm.getMonth(), wm.getTotalDuration());
    }

    @Transactional(readOnly = true)
    public int getMonthlyTotal(String trainerUsername, int year, int month) {
        return repository
                .findByTrainerUsernameAndYearAndMonth(trainerUsername, year, month)
                .map(TrainerMonthlyWorkload::getTotalDuration)
                .orElse(0);
    }

    @Transactional(readOnly = true)
    public TrainerMonthlySummaryResponse getSummary(String trainerUsername) {
        java.util.List<TrainerMonthlyWorkload> list = repository.findByTrainerUsername(trainerUsername);
        if (list.isEmpty()) {
            return new TrainerMonthlySummaryResponse(trainerUsername, null, null, false, java.util.Collections.emptyList());
        }

        String firstName = list.get(0).getTrainerFirstName();
        String lastName = list.get(0).getTrainerLastName();
        boolean active = list.get(0).isActive();

        Map<Integer, List<TrainerMonthlyWorkload>> byYear = list.stream()
                .collect(Collectors.groupingBy(TrainerMonthlyWorkload::getYear));

        java.util.List<YearSummaryDto> years = byYear.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    int year = entry.getKey();
                    java.util.List<MonthSummaryDto> months = entry.getValue().stream()
                            .sorted(Comparator.comparingInt(TrainerMonthlyWorkload::getMonth))
                            .map(wm -> new MonthSummaryDto(wm.getMonth(), wm.getTotalDuration()))
                            .collect(Collectors.toList());
                    return new YearSummaryDto(year, months);
                })
                .collect(Collectors.toList());

        return new TrainerMonthlySummaryResponse(trainerUsername, firstName, lastName, active, years);
    }
}
