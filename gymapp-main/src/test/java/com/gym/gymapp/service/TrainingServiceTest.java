package com.gym.gymapp.service;

import com.gym.gymapp.entity.Training;
import com.gym.gymapp.repository.TrainingRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainingServiceTest {
    @Test
    void getById_notFound() {
        TrainingRepository repo = mock(TrainingRepository.class);
        when(repo.findById(99L)).thenReturn(Optional.empty());
        TrainingService svc = new TrainingService(repo);
        assertThrows(RuntimeException.class, () -> svc.getById(99L));
    }
    @Test
    void create_callsSave() {
        TrainingRepository repo = mock(TrainingRepository.class);
        Training t = mock(Training.class);
        when(repo.save(any())).thenReturn(t);
        TrainingService svc = new TrainingService(repo);
        assertNotNull(svc.create(t));
        verify(repo, times(1)).save(t);
    }
}
