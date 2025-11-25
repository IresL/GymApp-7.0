package com.gym.gymapp.config;

import com.gym.gymapp.entity.User;
import com.gym.gymapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            String username = "admin";

            // თუ უკვე არსებობს, აღარ შევქმნათ თავიდან
            if (userRepository.findByUsername(username).isPresent()) {
                return;
            }

            User admin = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode("admin")) // პაროლი: admin
                    .firstName("Admin")
                    .lastName("User")
                    .role(User.Role.ADMIN)
                    .active(true)
                    .build();

            userRepository.save(admin);
        };
    }
}

