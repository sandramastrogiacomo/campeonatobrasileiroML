package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.controller.ClubeController;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubeRequestDTO;
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

@WebMvcTest (ClubeController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClubeService clubeService;

    @Test
    public void deveRetornarErros_quandoDTOInvalido() throws Exception {
        ClubeRequestDTO clubeInvalido = new ClubeRequestDTO();
        clubeInvalido.setNome("A");
        clubeInvalido.setEstado("");

        mockMvc.perform(post("/clubes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clubeInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0]").value(containsString("nome")))
                .andExpect(jsonPath("$[1]").value(containsString("estado")));
    }
}
