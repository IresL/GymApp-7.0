package com.gym.gymapp.service;

import com.gym.gymapp.dto.TraineeCreateRequest;
import com.gym.gymapp.dto.TraineeDto;
import com.gym.gymapp.entity.Trainee;
import com.gym.gymapp.entity.User;
import com.gym.gymapp.repository.TraineeRepository;
import com.gym.gymapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TraineeDto createTrainee(TraineeCreateRequest request) {
        String username = generateUsername(request.getFirstName(), request.getLastName());

        User user = User.builder()
                .username(username)
                // DTO-ში getPassword() არ გვაქვს, ამიტომ ვსვამთ default-ს
                .password(passwordEncoder.encode("password"))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(User.Role.TRAINEE)
                .active(true)
                .build();

        userRepository.save(user);

        Trainee trainee = Trainee.builder()
                .user(user)
                .address(request.getAddress())
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth()))
                .build();

        traineeRepository.save(trainee);

        return toDto(trainee);
    }

    public TraineeDto getTraineeById(Long id) {
        Trainee trainee = traineeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found: " + id));
        return toDto(trainee);
    }

    public List<TraineeDto> getAllTrainees() {
        return traineeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TraineeDto toDto(Trainee t) {
        TraineeDto dto = new TraineeDto();
        dto.setId(t.getId());
        dto.setUsername(t.getUser().getUsername());
        dto.setAddress(t.getAddress());
        dto.setDateOfBirth(String.valueOf(t.getDateOfBirth()));
        return dto;
    }

    private String generateUsername(String firstName, String lastName) {
        return (firstName + "." + lastName).toLowerCase();
    }
}
