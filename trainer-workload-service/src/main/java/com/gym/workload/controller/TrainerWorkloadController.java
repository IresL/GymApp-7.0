package com.gym.workload.controller;

import com.gym.workload.dto.TrainerMonthlySummaryResponse;
import com.gym.workload.dto.TrainerWorkloadRequest;
import com.gym.workload.service.TrainerWorkloadService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainers/{trainerUsername}/workload")
@RequiredArgsConstructor
public class TrainerWorkloadController {

    private static final Logger log = LoggerFactory.getLogger(TrainerWorkloadController.class);

    private final TrainerWorkloadService workloadService;

    @PostMapping
    public ResponseEntity<Void> postWorkload(@PathVariable String trainerUsername,
                                             @RequestBody TrainerWorkloadRequest request) {
        log.info("Received workload update request for trainer {} via REST", trainerUsername);
        if (request.getTrainerUsername() == null || request.getTrainerUsername().trim().isEmpty()) {
            request.setTrainerUsername(trainerUsername);
        }
        workloadService.applyWorkload(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/monthly")
    public ResponseEntity<Integer> getMonthlyWorkload(
            @PathVariable String trainerUsername,
            @RequestParam int year,
            @RequestParam int month) {

        int total = workloadService.getMonthlyTotal(trainerUsername, year, month);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/summary")
    public ResponseEntity<TrainerMonthlySummaryResponse> getSummary(
            @PathVariable String trainerUsername) {

        return ResponseEntity.ok(workloadService.getSummary(trainerUsername));
    }
}
