package com.gym.gymapp.service;

import com.gym.gymapp.entity.User;
import com.gym.gymapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    @Test
    void authenticate_ok() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder enc = mock(PasswordEncoder.class);
        when(repo.findByUsername("john")).thenReturn(Optional.of(new User("john", "HASH", true)));
        when(enc.matches("secret", "HASH")).thenReturn(true);
        AuthService svc = new AuthService(repo, enc);
        assertTrue(svc.authenticate("john", "secret"));
        verify(repo, times(1)).findByUsername("john");
    }
    @Test
    void authenticate_fail() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder enc = mock(PasswordEncoder.class);
        when(repo.findByUsername("john")).thenReturn(Optional.empty());
        AuthService svc = new AuthService(repo, enc);
        assertFalse(svc.authenticate("john", "secret"));
    }
}
