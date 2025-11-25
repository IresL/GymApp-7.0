package com.gym.gymapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication

@EnableFeignClients(basePackages = "com.gym.gymapp.integration")
@org.springframework.boot.autoconfigure.domain.EntityScan("com.gym.gymapp.entity")
@org.springframework.data.jpa.repository.config.EnableJpaRepositories("com.gym.gymapp.repository")
public class
GymApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymApplication.class, args);
    }
}
