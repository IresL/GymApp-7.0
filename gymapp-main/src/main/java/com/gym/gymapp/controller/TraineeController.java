package com.gym.gymapp.controller;

import com.gym.gymapp.dto.TraineeCreateRequest;
import com.gym.gymapp.dto.TraineeDto;
import com.gym.gymapp.facade.GymFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainees")
public class TraineeController {

    private final GymFacade gymFacade;

    public TraineeController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @PostMapping
    public ResponseEntity<TraineeDto> create(@RequestBody TraineeCreateRequest request) {
        return ResponseEntity.ok(gymFacade.createTrainee(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraineeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(gymFacade.getTraineeById(id));
    }

    @GetMapping
    public ResponseEntity<List<TraineeDto>> getAll() {
        return ResponseEntity.ok(gymFacade.getAllTrainees());
    }
}
