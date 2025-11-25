package com.gym.gymapp.controller;

import com.gym.gymapp.dto.TrainerCreateRequest;
import com.gym.gymapp.dto.TrainerDto;
import com.gym.gymapp.facade.GymFacade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final GymFacade gymFacade;

    public TrainerController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @PostMapping
    public ResponseEntity<TrainerDto> create(@RequestBody TrainerCreateRequest request) {
        return ResponseEntity.ok(gymFacade.createTrainer(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(gymFacade.getTrainerById(id));
    }

    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAll() {
        return ResponseEntity.ok(gymFacade.getAllTrainers());
    }
}
