package com.gym.gymapp.service;

import com.gym.gymapp.dto.AuthResponseDto;
import com.gym.gymapp.entity.User;
import com.gym.gymapp.exception.UnauthorizedException;
import com.gym.gymapp.repository.UserRepository;
import com.gym.gymapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;

    public AuthResponseDto login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        // ბლოკის შემოწმება
        if (loginAttemptService.isBlocked(user)) {
            throw new UnauthorizedException("Account temporarily locked. Try again later.");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            if (authentication.isAuthenticated()) {
                loginAttemptService.resetAttempts(user);
                String token = jwtService.generateToken(username);
                return new AuthResponseDto(token, LocalDateTime.now().plusMinutes(60).toString());
            } else {
                loginAttemptService.recordFailedAttempt(user);
                throw new UnauthorizedException("Invalid username or password");
            }
        } catch (Exception e) {
            loginAttemptService.recordFailedAttempt(user);
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    public void logout(String authHeader) {
        // Stateless რეჟიმში არაფერია გასაკეთებელი
    }
}
