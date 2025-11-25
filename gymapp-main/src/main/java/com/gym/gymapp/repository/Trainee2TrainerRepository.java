package com.gym.gymapp.repository;

import com.gym.gymapp.entity.Trainee2Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Trainee2TrainerRepository extends JpaRepository<Trainee2Trainer, Long> {
}
