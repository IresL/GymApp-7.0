package com.gym.gymapp.service;

import com.gym.gymapp.entity.Trainer;
import com.gym.gymapp.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class TrainerServiceTest {
    @Test
    void create_setsId() {
        TrainerRepository repo = mock(TrainerRepository.class);
        PasswordEncoder enc = mock(PasswordEncoder.class);
        when(enc.encode(anyString())).thenReturn("ENC");
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        TrainerService svc = new TrainerService(repo, enc);
        TrainerDto dto = new TrainerDto(null, "u2", "f","l","spec", true);
        TrainerDto out = svc.create(dto);
        assertEquals("u2", out.getUsername());
    }
    @Test
    void getById_ok() {
        TrainerRepository repo = mock(TrainerRepository.class);
        PasswordEncoder enc = mock(PasswordEncoder.class);
        Trainer t = new Trainer("u2","p",true,"f","l","spec");
        when(repo.findById(2L)).thenReturn(Optional.of(t));
        TrainerService svc = new TrainerService(repo, enc);
        TrainerDto out = svc.getById(2L);
        assertEquals("u2", out.getUsername());
    }
}
