package com.gym.gymapp.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "trainer-workload-service", path = "/api/trainers")
public interface TrainerWorkloadFeignClient {

    @PostMapping("/{trainerUsername}/workload")
    void sendWorkload(@PathVariable("trainerUsername") String trainerUsername,
                      @RequestBody TrainerWorkloadRequest request,
                      @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                      @RequestHeader("X-Transaction-Id") String transactionId);
}
