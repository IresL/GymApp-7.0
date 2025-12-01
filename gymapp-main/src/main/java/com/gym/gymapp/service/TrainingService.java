package com.gym.gymapp.service;

import com.gym.gymapp.dto.TrainingCreateRequest;
import com.gym.gymapp.dto.TrainingDto;
import com.gym.gymapp.entity.Trainee;
import com.gym.gymapp.entity.Trainer;
import com.gym.gymapp.entity.Training;
import com.gym.gymapp.entity.TrainingType;
import com.gym.gymapp.repository.TraineeRepository;
import com.gym.gymapp.repository.TrainerRepository;
import com.gym.gymapp.repository.TrainingRepository;
import com.gym.gymapp.repository.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    /**
     * ყველა ტრენინგის წამოღება
     */
    public List<TrainingDto> getAll() {
        return trainingRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * კონკრეტული ტრენინგის წამოღება ID-ით
     */
    public TrainingDto getTrainingById(Long id) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Training not found: " + id));
        return toDto(training);
    }

    /**
     * ტრენინგის შექმნა Request-ის მიხედვით
     */
    public TrainingDto create(TrainingCreateRequest request) {
        Trainee trainee = traineeRepository.findByUserUsername(request.getTraineeUsername())
                .orElseThrow(() ->
                        new IllegalArgumentException("Trainee not found: " + request.getTraineeUsername()));

        Trainer trainer = trainerRepository.findByUserUsername(request.getTrainerUsername())
                .orElseThrow(() ->
                        new IllegalArgumentException("Trainer not found: " + request.getTrainerUsername()));

        TrainingType trainingType = trainingTypeRepository.findById(request.getTrainingTypeId())
                .orElseThrow(() ->
                        new IllegalArgumentException("TrainingType not found: " + request.getTrainingTypeId()));

        LocalDate date = LocalDate.parse(request.getTrainingDate());

        Training training = Training.builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingType(trainingType)
                .trainingDate(date)
                .trainingDuration(request.getDurationMinutes())
                .trainingName(request.getTrainingName())
                .build();

        Training saved = trainingRepository.save(training);
        return toDto(saved);
    }

    /**
     * ტრენინგის წაშლა ID-ით
     */
    public void delete(Long id) {
        if (!trainingRepository.existsById(id)) {
            throw new IllegalArgumentException("Training not found: " + id);
        }
        trainingRepository.deleteById(id);
    }

    /**
     * Entity → DTO გარდაქმნა
     */
    private TrainingDto toDto(Training t) {
        TrainingDto dto = new TrainingDto();
        dto.setId(t.getId());
        dto.setTrainerUsername(t.getTrainer().getUser().getUsername());
        dto.setTraineeUsername(t.getTrainee().getUser().getUsername());
        dto.setTrainingName(t.getTrainingName());
        dto.setTrainingType(t.getTrainingType().getTrainingTypeName());
        dto.setTrainingDate(String.valueOf(t.getTrainingDate()));
        dto.setTrainingDuration(t.getTrainingDuration());
        return dto;
    }
}
