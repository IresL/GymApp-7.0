package com.gym.gymapp.integration;

import com.gym.gymapp.dto.TrainingDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TrainerWorkloadClient {

    private static final Logger log = LoggerFactory.getLogger(TrainerWorkloadClient.class);

    private final TrainerWorkloadFeignClient feignClient;

    public TrainerWorkloadClient(TrainerWorkloadFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @CircuitBreaker(name = "workloadService", fallbackMethod = "fallback")
    public void sendTrainingAdded(TrainingDto training, String authHeader) {
        send(training, authHeader, TrainerWorkloadRequest.ActionType.ADD);
    }

    @CircuitBreaker(name = "workloadService", fallbackMethod = "fallback")
    public void sendTrainingDeleted(TrainingDto training, String authHeader) {
        send(training, authHeader, TrainerWorkloadRequest.ActionType.DELETE);
    }

    private void send(TrainingDto training, String authHeader, TrainerWorkloadRequest.ActionType action) {
        if (training == null) {
            log.error("Cannot send workload update: training is null");
            throw new IllegalArgumentException("Training must not be null when sending workload update");
        }
        if (training.getTrainerUsername() == null || training.getTrainerUsername().trim().isEmpty()) {
            log.error("Cannot send workload update: trainerUsername is null or blank for training id={}", training.getId());
            throw new IllegalArgumentException("trainerUsername must not be null or blank when sending workload update");
        }

        TrainerWorkloadRequest payload = new TrainerWorkloadRequest();
        payload.setTrainerUsername(training.getTrainerUsername());
        payload.setTrainerFirstName(null); // can be filled from Trainer in future
        payload.setTrainerLastName(null);
        payload.setActive(true);
        payload.setTrainingDate(LocalDate.parse(training.getTrainingDate()));
        payload.setTrainingDuration(training.getTrainingDuration());
        payload.setActionType(action);

        String txId = MDC.get("transactionId");
        if (txId == null || txId.trim().isEmpty()) {
            txId = "missing-tx-id";
        }

        log.info("Sending workload update via Feign: trainer={}, action={}, txId={}",
                payload.getTrainerUsername(), action, txId);

        feignClient.sendWorkload(
                payload.getTrainerUsername(),
                payload,
                authHeader != null ? authHeader : "",
                txId
        );
    }

    @SuppressWarnings("unused")
    private void fallback(TrainingDto training, String authHeader, Throwable ex) {
        String txId = MDC.get("transactionId");
        log.warn("Workload service call failed for trainer {} (txId={}): {}",
                training != null ? training.getTrainerUsername() : "null",
                txId,
                ex.getMessage());
    }
}
