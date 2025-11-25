package com.gym.gymapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "trainee2trainer",
        uniqueConstraints = @UniqueConstraint(name = "uk_trainee_trainer", columnNames = {"trainee_id","trainer_id"}))
public class Trainee2Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trainee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_t2t_trainee"))
    private Trainee trainee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trainer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_t2t_trainer"))
    private Trainer trainer;
}
