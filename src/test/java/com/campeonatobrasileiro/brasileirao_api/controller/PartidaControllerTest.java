package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.PartidaRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.PartidaResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.PartidaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PartidaController.class)
public class PartidaControllerTest {

    @Autowired
    private PartidaController partidaController;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartidaService partidaService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCadastrarPartidaComSucesso() throws Exception {

        PartidaRequestDTO partidaRequestDTO = new PartidaRequestDTO();
        partidaRequestDTO.setDataHora(LocalDateTime.now().plusDays(1));
        partidaRequestDTO.setClubeMandanteId(1L);
        partidaRequestDTO.setClubeVisitanteId(2L);
        partidaRequestDTO.setEstadioId(1L);
        partidaRequestDTO.setGolsMandante(2);
        partidaRequestDTO.setGolsVisitante(1);

        PartidaResponseDTO partidaResponseDTO = new PartidaResponseDTO(
                1L,
                partidaRequestDTO.getDataHora(),
                "Palmeiras",
                "Flamengo",
                "Allianz Park",
                2,
                1);

        Mockito.when(partidaService.cadastrarPartida(any())).thenReturn(partidaResponseDTO);

        mockMvc.perform(post("/partidas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partidaRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadio").value("Allianz Park"))
                .andExpect(jsonPath("$.clubeMandante").value("Palmeiras"))
                .andExpect(jsonPath("$.clubeVisitante").value("Flamengo"))
                .andExpect(jsonPath("$.golsMandante").value(2))
                .andExpect(jsonPath("$.golsVisitante").value(1));

    }

    @Test
    void deveListarPartidasComSucesso() throws Exception {
        PartidaResponseDTO partidaResponseDTO = new PartidaResponseDTO(
                1L,
                LocalDateTime.now(),
                "Palmeiras",
                "Flamengo",
                "Allianz Park",
                2,
                1);

        PageImpl<PartidaResponseDTO> page = new PageImpl<>(List.of(partidaResponseDTO), PageRequest.of(0, 10), 2);

        Mockito.when(partidaService.listarPartidas(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/partidas")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].estadio").value("Allianz Park"))
                .andExpect(jsonPath("$.content[0].clubeMandante").value("Palmeiras"))
                .andExpect(jsonPath("$.content[0].clubeVisitante").value("Flamengo"))
                .andExpect(jsonPath("$.content[0].golsMandante").value(2))
                .andExpect(jsonPath("$.content[0].golsVisitante").value(1));
    }

    @Test
    void deveBuscarPartidaPorId() throws Exception {
        PartidaResponseDTO partidaResponseDTO = new PartidaResponseDTO(
                1L,
                LocalDateTime.now(),
                "Palmeiras",
                "Flamengo",
                "Allianz Park",
                2,
                1);

        Mockito.when(partidaService.buscarPorId(1L)).thenReturn(partidaResponseDTO);

        mockMvc.perform(get("/partidas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadio").value("Allianz Park"))
                .andExpect(jsonPath("$.clubeMandante").value("Palmeiras"))
                .andExpect(jsonPath("$.clubeVisitante").value("Flamengo"))
                .andExpect(jsonPath("$.golsMandante").value(2))
                .andExpect(jsonPath("$.golsVisitante").value(1));
    }

    @Test
    void deveListarPartidaPorEstadio() throws Exception {
        PartidaResponseDTO partidaResponseDTO = new PartidaResponseDTO(
                1L,
                LocalDateTime.now().plusDays(1),
                "Palmeiras",
                "Flamengo",
                "Allianz Park",
                2,
                1);
        PageImpl<PartidaResponseDTO> page = new PageImpl<>(List.of(partidaResponseDTO), PageRequest.of(0, 10), 2);

        Mockito.when(partidaService.listarPorEstadio(Mockito.eq(1L),any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/partidas/estadio/1")
                .param("page","0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].estadio").value("Allianz Park"));
    }

    @Test
    void deveDeletarPartidaPorId() throws Exception {

        mockMvc.perform(delete("/partidas/1"))
                .andExpect(status().isNoContent());

    }

}
