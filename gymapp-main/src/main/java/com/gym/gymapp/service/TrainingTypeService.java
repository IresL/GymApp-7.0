package com.gym.gymapp.service;

import com.gym.gymapp.dto.TrainingTypeCreateRequest;
import com.gym.gymapp.dto.TrainingTypeDto;
import com.gym.gymapp.entity.TrainingType;
import com.gym.gymapp.repository.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {

    private final TrainingTypeRepository repo;

    public TrainingTypeDto createTrainingType(TrainingTypeCreateRequest request) {
        TrainingType type = new TrainingType();
        type.setTrainingTypeName(request.getTrainingTypeName());
        repo.save(type);
        return toDto(type);
    }

    public List<TrainingTypeDto> getAllTrainingTypes() {
        return repo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TrainingTypeDto toDto(TrainingType t) {
        TrainingTypeDto dto = new TrainingTypeDto();
        dto.setId(t.getId());
        dto.setTrainingTypeName(t.getTrainingTypeName());
        return dto;
    }
}
