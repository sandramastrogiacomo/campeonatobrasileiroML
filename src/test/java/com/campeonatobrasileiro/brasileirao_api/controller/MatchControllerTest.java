package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.MatchRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.MatchResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.MatchService;
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

@WebMvcTest(controllers = MatchController.class)
public class MatchControllerTest {

    @Autowired
    private MatchController matchController;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MatchService matchService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerMatchSuccessfully() throws Exception {

        MatchRequestDTO matchRequestDTO = new MatchRequestDTO();
        matchRequestDTO.setDateTime(LocalDateTime.now().plusDays(1));
        matchRequestDTO.setHomeClubId(1L);
        matchRequestDTO.setAwayClubId(2L);
        matchRequestDTO.setStadiumId(1L);
        matchRequestDTO.setHomeGoals(2);
        matchRequestDTO.setAwayGoals(1);

        MatchResponseDTO matchResponseDTO = new MatchResponseDTO(
                1L,
                matchRequestDTO.getDateTime(),
                "Palmeiras",
                "Flamengo",
                "Allianz Park",
                2,
                1);

        Mockito.when(matchService.registerMatch(any())).thenReturn(matchResponseDTO);

        mockMvc.perform(post("/matches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matchRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stadium").value("Allianz Park"))
                .andExpect(jsonPath("$.homeClub").value("Palmeiras"))
                .andExpect(jsonPath("$.awayClub").value("Flamengo"))
                .andExpect(jsonPath("$.homeGoals").value(2))
                .andExpect(jsonPath("$.awayGoals").value(1));

    }

    @Test
    void listMatchesSuccessfully() throws Exception {
        MatchResponseDTO matchResponseDTO = new MatchResponseDTO(
                1L,
                LocalDateTime.now(),
                "Palmeiras",
                "Flamengo",
                "Allianz Park",
                2,
                1);

        PageImpl<MatchResponseDTO> page = new PageImpl<>(List.of(matchResponseDTO), PageRequest.of(0, 10), 2);

        Mockito.when(matchService.listMatches(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/matches")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].stadium").value("Allianz Park"))
                .andExpect(jsonPath("$.content[0].homeClub").value("Palmeiras"))
                .andExpect(jsonPath("$.content[0].awayClub").value("Flamengo"))
                .andExpect(jsonPath("$.content[0].homeGoals").value(2))
                .andExpect(jsonPath("$.content[0].awayGoals").value(1));
    }

    @Test
    void findMatchById() throws Exception {
        MatchResponseDTO matchResponseDTO = new MatchResponseDTO(
                1L,
                LocalDateTime.now(),
                "Palmeiras",
                "Flamengo",
                "Allianz Park",
                2,
                1);

        Mockito.when(matchService.findById(1L)).thenReturn(matchResponseDTO);

        mockMvc.perform(get("/matches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stadium").value("Allianz Park"))
                .andExpect(jsonPath("$.homeClub").value("Palmeiras"))
                .andExpect(jsonPath("$.awayClub").value("Flamengo"))
                .andExpect(jsonPath("$.homeGoals").value(2))
                .andExpect(jsonPath("$.awayGoals").value(1));
    }

    @Test
    void listMatchByStadium() throws Exception {
        MatchResponseDTO matchResponseDTO = new MatchResponseDTO(
                1L,
                LocalDateTime.now().plusDays(1),
                "Palmeiras",
                "Flamengo",
                "Allianz Park",
                2,
                1);
        PageImpl<MatchResponseDTO> page = new PageImpl<>(List.of(matchResponseDTO), PageRequest.of(0, 10), 2);

        Mockito.when(matchService.listMatchesByStadium(Mockito.eq(1L),any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/matches/stadium/1")
                .param("page","0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].stadium").value("Allianz Park"));
    }

    @Test
    void deletedMatchById () throws Exception {

        mockMvc.perform(delete("/matches/1"))
                .andExpect(status().isNoContent());

    }

}
