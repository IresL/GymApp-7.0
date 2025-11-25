package com.gym.gymapp.service;

import com.gym.gymapp.dto.TrainerCreateRequest;
import com.gym.gymapp.dto.TrainerDto;
import com.gym.gymapp.entity.Trainer;
import com.gym.gymapp.entity.User;
import com.gym.gymapp.repository.TrainerRepository;
import com.gym.gymapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TrainerDto createTrainer(TrainerCreateRequest request) {
        String username = generateUsername(request.getFirstName(), request.getLastName());

        User user = User.builder()
                .username(username)
                // DTO-ში getPassword() არ გვაქვს, ამიტომ ვსვამთ default-ს
                .password(passwordEncoder.encode("password"))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(User.Role.TRAINER)
                .active(true)
                .build();

        userRepository.save(user);

        Trainer trainer = Trainer.builder()
                .user(user)
                .specialization(request.getSpecialization())
                .build();

        trainerRepository.save(trainer);

        return toDto(trainer);
    }

    public TrainerDto getTrainerById(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + id));
        return toDto(trainer);
    }

    public List<TrainerDto> getAllTrainers() {
        return trainerRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TrainerDto toDto(Trainer trainer) {
        TrainerDto dto = new TrainerDto();
        dto.setId(trainer.getId());
        dto.setUsername(trainer.getUser().getUsername());
        dto.setSpecialization(trainer.getSpecialization());
        return dto;
    }

    private String generateUsername(String firstName, String lastName) {
        return (firstName + "." + lastName).toLowerCase();
    }
}
