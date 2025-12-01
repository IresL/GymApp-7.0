package com.gym.workload.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trainer_monthly_workload")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerMonthlyWorkload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean active;

    // month keyword-ის პრობლემა რომ არ იყოს, სვეტი ერქვას workload_month
    @Column(name = "workload_month", nullable = false)
    private Integer month;

    @Column(name = "total_duration", nullable = false)
    private Integer totalDuration;

    @Column(name = "trainer_first_name")
    private String trainerFirstName;

    @Column(name = "trainer_last_name")
    private String trainerLastName;

    @Column(name = "trainer_username")
    private String trainerUsername;

    // year-საც ავუცილოთ keyword-ის რისკი
    @Column(name = "workload_year", nullable = false)
    private Integer year;
}
