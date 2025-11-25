package com.gym.gymapp.controller;

import com.gym.gymapp.dto.TrainingTypeCreateRequest;
import com.gym.gymapp.dto.TrainingTypeDto;
import com.gym.gymapp.facade.GymFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-types")
public class TrainingTypeController {

    private final GymFacade gymFacade;

    public TrainingTypeController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @PostMapping
    public ResponseEntity<TrainingTypeDto> create(@RequestBody TrainingTypeCreateRequest request) {
        return ResponseEntity.ok(gymFacade.createTrainingType(request));
    }

    @GetMapping
    public ResponseEntity<List<TrainingTypeDto>> getAll() {
        return ResponseEntity.ok(gymFacade.getAllTrainingTypes());
    }
}
