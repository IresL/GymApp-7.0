package com.gym.gymapp.controller;

import com.gym.gymapp.dto.TrainingCreateRequest;
import com.gym.gymapp.dto.TrainingDto;
import com.gym.gymapp.facade.GymFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    private final GymFacade gymFacade;

    public TrainingController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @PostMapping
    public ResponseEntity<TrainingDto> create(@RequestBody TrainingCreateRequest request,
                                             @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(gymFacade.createTraining(request, authHeader));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(gymFacade.getTrainingById(id));
    }

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAll() {
        return ResponseEntity.ok(gymFacade.getAllTrainings());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        gymFacade.deleteTraining(id, authHeader);
        return ResponseEntity.noContent().build();
    }

}
