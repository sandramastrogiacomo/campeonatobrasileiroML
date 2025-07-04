package com.campeonatobrasileiro.brasileirao_api.handler;

import com.campeonatobrasileiro.brasileirao_api.controller.ClubController;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.service.ClubService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasItem;

@WebMvcTest (ClubController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClubService clubService;

    @Test
    public void returnErrorsInvalidDTO() throws Exception {
        ClubRequestDTO invalidClub = new ClubRequestDTO();
        invalidClub.setName("A");
        invalidClub.setState("");
        invalidClub.setActive(null);
        invalidClub.setStadiumId(null);

        mockMvc.perform(post("/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClub)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$",hasItem(containsString("name"))))
                .andExpect(jsonPath("$",hasItem(containsString("state"))))
                .andExpect(jsonPath("$",hasItem(containsString("stadiumId"))))
                .andExpect(jsonPath("$",hasItem(containsString("active"))));

    }
}
