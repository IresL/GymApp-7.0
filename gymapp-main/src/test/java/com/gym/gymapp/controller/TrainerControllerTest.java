package com.gym.gymapp.controller;

import com.gym.gymapp.dto.TrainerDto;
import com.gym.gymapp.facade.GymFacade;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(TrainerController.class)
public class TrainerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GymFacade facade;
    @MockBean(name="trainerRequestsCounter")
    private Counter counter;

    @Test
    void create_ok() throws Exception {
        when(facade.createTrainer(any())).thenReturn(new TrainerDto());
        mockMvc.perform(post("/api/trainers")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"u2\",\"firstName\":\"f\",\"lastName\":\"l\",\"specialization\":\"spec\",\"active\":true}"))
            .andExpect(status().isOk());
    }
}
