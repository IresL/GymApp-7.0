package com.gym.gymapp.controller;

import com.gym.gymapp.dto.TraineeDto;
import com.gym.gymapp.facade.GymFacade;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TraineeController.class)
public class TraineeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GymFacade facade;
    @MockBean(name="traineeRequestsCounter")
    private Counter counter;

    @Test
    void getAll_ok() throws Exception {
        when(facade.getAllTrainees()).thenReturn(List.of());
        mockMvc.perform(get("/api/trainees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void create_ok() throws Exception {
        when(facade.createTrainee(any())).thenReturn(new TraineeDto());
        mockMvc.perform(post("/api/trainees")
                .contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"u1\",\"firstName\":\"f\",\"lastName\":\"l\",\"active\":true}"))
                .andExpect(status().isOk());
    }
}
