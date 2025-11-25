package com.gym.gymapp.facade;

import com.gym.gymapp.dto.TraineeCreateRequest;
import com.gym.gymapp.dto.TraineeDto;
import com.gym.gymapp.dto.TrainerCreateRequest;
import com.gym.gymapp.dto.TrainerDto;
import com.gym.gymapp.dto.TrainingCreateRequest;
import com.gym.gymapp.dto.TrainingDto;
import com.gym.gymapp.dto.TrainingTypeCreateRequest;
import com.gym.gymapp.dto.TrainingTypeDto;
import com.gym.gymapp.integration.TrainerWorkloadClient;
import com.gym.gymapp.service.TraineeService;
import com.gym.gymapp.service.TrainerService;
import com.gym.gymapp.service.TrainingService;
import com.gym.gymapp.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final TrainingTypeService trainingTypeService;
    private final TrainerWorkloadClient trainerWorkloadClient;

    // ===== Trainee =====

    public TraineeDto createTrainee(TraineeCreateRequest request) {
        return traineeService.createTrainee(request);
    }

    public TraineeDto getTraineeById(Long id) {
        return traineeService.getTraineeById(id);
    }

    public List<TraineeDto> getAllTrainees() {
        return traineeService.getAllTrainees();
    }

    // ===== Trainer =====

    public TrainerDto createTrainer(TrainerCreateRequest request) {
        return trainerService.createTrainer(request);
    }

    public TrainerDto getTrainerById(Long id) {
        return trainerService.getTrainerById(id);
    }

    public List<TrainerDto> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    // ===== Training Type =====

    public TrainingTypeDto createTrainingType(TrainingTypeCreateRequest request) {
        return trainingTypeService.createTrainingType(request);
    }

    public List<TrainingTypeDto> getAllTrainingTypes() {
        return trainingTypeService.getAllTrainingTypes();
    }

    // ===== Training =====

    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAll();
    }

    public TrainingDto getTrainingById(Long id) {
        return trainingService.getTrainingById(id);
    }

    public TrainingDto createTraining(TrainingCreateRequest request, String authHeader) {
        TrainingDto dto = trainingService.create(request);
        trainerWorkloadClient.sendTrainingAdded(dto, authHeader);
        return dto;
    }

    public void deleteTraining(Long id) {
        trainingService.delete(id);
    }

    public void deleteTraining(Long id, String authHeader) {
        TrainingDto dto = trainingService.getTrainingById(id);
        trainingService.delete(id);
        trainerWorkloadClient.sendTrainingDeleted(dto, authHeader);
    }
}
