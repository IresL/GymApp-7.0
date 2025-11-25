package com.gym.gymapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "training_type",
        uniqueConstraints = @UniqueConstraint(name = "uk_training_type_name", columnNames = "training_type_name"))
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_type_name", nullable = false, length = 100)
    private String trainingTypeName;
}
