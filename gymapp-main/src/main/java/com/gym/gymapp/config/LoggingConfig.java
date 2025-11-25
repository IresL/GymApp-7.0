package com.gym.gymapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Request/response ჭრილში ბაზური ლოგირება (ჰედერებიდან სენსიტიურის გარეშე).
 */
@Configuration
public class LoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter f = new CommonsRequestLoggingFilter();
        f.setIncludeClientInfo(true);
        f.setIncludeQueryString(true);
        f.setIncludePayload(true);
        f.setIncludeHeaders(false); // სენსიტიურ ჰედერებს არ ვლოგავთ
        f.setMaxPayloadLength(1_000);
        return f;
    }
}
