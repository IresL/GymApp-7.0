package com.gym.gymapp.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ActuatorConfig {

    @Bean
    public InfoContributor appInfoContributor() {
        return (Info.Builder builder) -> {
            Map<String, Object> appDetails = new HashMap<>();
            appDetails.put("name", "GymApp");
            appDetails.put("version", "6.0"); // თუ გინდა, 4.0-საც დატოვებ
            appDetails.put("description", "Gym Application with Security & JWT");

            builder.withDetail("app", appDetails);
        };
    }
}
