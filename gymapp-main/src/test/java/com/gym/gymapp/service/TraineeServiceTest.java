package com.gym.gymapp.service;

import com.gym.gymapp.dto.TraineeDto;
import com.gym.gymapp.entity.Trainee;
import com.gym.gymapp.repository.TraineeRepository;
//import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

public class TraineeServiceTest {
//    @Test
//    void create_setsId() {
//        TraineeRepository repo = mock(TraineeRepository.class);
//        PasswordEncoder enc = mock(PasswordEncoder.class);
//        when(enc.encode(anyString())).thenReturn("ENC");
//        when(repo.save(any())).thenAnswer(inv -> {
//            Trainee t = inv.getArgument(0);
//            // simulate ID assignment (not visible via entity, so nothing)
//            return t;
//        });
//        TraineeService svc = new TraineeService(repo, enc);
//        TraineeDto dto = new TraineeDto(null, "u1", "f", "l", LocalDate.of(2000,1,1), true);
//        TraineeDto out = svc.create(dto);
//        assertEquals("u1", out.getUsername());
//    }
//    @Test
//    void getById_ok() {
//        TraineeRepository repo = mock(TraineeRepository.class);
//        PasswordEncoder enc = mock(PasswordEncoder.class);
//        Trainee t = new Trainee("u1", "p", true, "f","l", LocalDate.of(2000,1,1));
//        when(repo.findById(1L)).thenReturn(Optional.of(t));
//        TraineeService svc = new TraineeService(repo, enc);
//        TraineeDto out = svc.getById(1L);
//        assertEquals("u1", out.getUsername());
//    }
}
