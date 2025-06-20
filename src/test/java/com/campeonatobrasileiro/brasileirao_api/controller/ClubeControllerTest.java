package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubeRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubeResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubeRepository;
import com.campeonatobrasileiro.brasileirao_api.service.ClubeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClubeController.class)
public class ClubeControllerTest {

   @Autowired
    private MockMvc mockMvc;

   @MockBean
    private ClubeService clubeService;

   @Autowired
    private ObjectMapper objectMapper;

   @Test
   void deveCadastrarClubeComSucesso() throws Exception {
       ClubeRequestDTO clubeRequestDTO = new ClubeRequestDTO("Palmeiras", "São Paulo");
       ClubeResponseDTO clubeResponseDTO = new ClubeResponseDTO(1L, "Palmeiras", "SP", true);

       Mockito.when(clubeService.cadastrarClube(any())).thenReturn(clubeResponseDTO);

       mockMvc.perform(post("/clubes")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(clubeRequestDTO)))
               .andExpect(status().IsCreated())
               .andExpect(jsonPath("$.id").value(10))
               .andExpect(jsonPath("$.nome").value("Palmeiras"))
               .andExpect(jsonPath("$.estado").value("SP"));
   }



}
