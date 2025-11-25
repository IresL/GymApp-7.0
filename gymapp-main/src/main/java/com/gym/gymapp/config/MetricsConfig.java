package com.gym.gymapp.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MetricsConfig {

    @Value("${spring.application.name:gymapp}")
    private String appName;

    @Value("${spring.profiles.active:dev}")
    private String profile;

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> commonTagsCustomizer() {
        return registry -> registry.config()
                .commonTags(Arrays.asList(
                        Tag.of("app", appName),
                        Tag.of("env", profile)
                ))
                .meterFilter(MeterFilter.denyNameStartsWith("jvm.gc.pause.bucket"));
    }
}
