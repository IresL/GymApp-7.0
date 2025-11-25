package com.gym.workload.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "trainer_monthly_workload")
@Getter
@Setter
public class TrainerMonthlyWorkload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean active;

    private int year;
    private int month; // 1-12

    private int totalDuration; // minutes
}
