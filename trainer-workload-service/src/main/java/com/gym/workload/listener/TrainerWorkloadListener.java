package com.gym.workload.listener;

import com.gym.workload.config.JmsConfig;
import com.gym.workload.dto.TrainerWorkloadRequest;
import com.gym.workload.service.TrainerWorkloadService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TrainerWorkloadListener {

    private static final Logger log = LoggerFactory.getLogger(TrainerWorkloadListener.class);

    private final TrainerWorkloadService workloadService;

    @JmsListener(destination = JmsConfig.TRAINER_WORKLOAD_QUEUE)
    public void handleTrainerWorkload(TrainerWorkloadRequest request,
                                      @Headers Map<String, Object> headers) {

        Object txHeader = headers.get("X-Transaction-Id");
        String txId = txHeader != null ? txHeader.toString() : null;
        if (txId != null && txId.trim().length() > 0) {
            MDC.put("transactionId", txId);
        }

        try {
            log.info("Received workload message via JMS: trainer={}, action={}, txId={}",
                    request.getTrainerUsername(),
                    request.getActionType(),
                    txId);

            workloadService.applyWorkload(request);
        } finally {
            MDC.remove("transactionId");
        }
    }
}
