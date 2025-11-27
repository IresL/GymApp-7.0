package com.gym.gymapp.integration;

import com.gym.gymapp.config.JmsConfig;
import com.gym.gymapp.dto.TrainingDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TrainerWorkloadClient {

    private static final Logger log = LoggerFactory.getLogger(TrainerWorkloadClient.class);

    private final JmsTemplate jmsTemplate;

    public TrainerWorkloadClient(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @CircuitBreaker(name = "workloadService", fallbackMethod = "fallback")
    public void sendTrainingAdded(TrainingDto training, String authHeader) {
        send(training, TrainerWorkloadRequest.ActionType.ADD);
    }

    @CircuitBreaker(name = "workloadService", fallbackMethod = "fallback")
    public void sendTrainingDeleted(TrainingDto training, String authHeader) {
        send(training, TrainerWorkloadRequest.ActionType.DELETE);
    }

    private void send(TrainingDto training, TrainerWorkloadRequest.ActionType action) {
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

        log.info("Sending workload update via JMS: trainer={}, action={}, txId={}",
                payload.getTrainerUsername(), action, txId);

        final String finalTxId = txId;
        jmsTemplate.convertAndSend(JmsConfig.TRAINER_WORKLOAD_QUEUE, payload, message -> {
            message.setStringProperty("X-Transaction-Id", finalTxId);
            return message;
        });
    }

    @SuppressWarnings("unused")
    private void fallback(TrainingDto training, String authHeader, Throwable ex) {
        String txId = MDC.get("transactionId");
        log.warn("Workload message send failed for trainer {} (txId={}): {}",
                training != null ? training.getTrainerUsername() : "null",
                txId,
                ex.getMessage());
    }
}
