package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRankingResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubStatsResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.ClubService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ClubController.class)
public class ClubControllerTest {

   @Mock
    private MockMvc mockMvc;

   @Mock
    private ClubService clubService;

   @Mock
    private ObjectMapper objectMapper;


    @Test
   void createClubSuccessfully() throws Exception {
       ClubRequestDTO clubRequestDTO = new ClubRequestDTO();
       clubRequestDTO.setName("Palmeiras");
       clubRequestDTO.setState("SP");
       clubRequestDTO.setActive(true);
       clubRequestDTO.setStadiumId(1L);

       ClubResponseDTO clubResponseDTO = new ClubResponseDTO(1L,"Palmeiras","SP", true,
               "Allianz Park");

       Mockito.when(clubService.createClub(any())).thenReturn(clubResponseDTO);

       mockMvc.perform(post("/clubs")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(clubRequestDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.name").value("Palmeiras"))
               .andExpect(jsonPath("$.state").value("SP"))
               .andExpect(jsonPath("$.stadiumName").value("Allianz Park"));

       Mockito.verify(clubService, Mockito.times(1)).createClub(any());

   }

    @Test
    void findClubByIdSuccessfully() throws Exception {
       ClubResponseDTO clubResponseDTO = new ClubResponseDTO(1L, "Palmeiras", "SP", true,null);

       Mockito.when(clubService.findById(1L)).thenReturn(clubResponseDTO);

       mockMvc.perform(get("/clubs/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Palmeiras"))
               .andExpect(jsonPath("$.state").value("SP"));
    }

    @Test
    void findClubByNameSuccessfully() throws Exception {
       String name = "Palmeiras";

       ClubResponseDTO clubResponseDTO= new ClubResponseDTO(1L, "Palmeiras", "SP", true,null);
        List<ClubResponseDTO> list = List.of(clubResponseDTO);

        Mockito.when(clubService.findByNameContainingIgnoreCase(name)).thenReturn(list);

        mockMvc.perform(get("/clubs/name/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Palmeiras"))
                .andExpect(jsonPath("$[0].state").value("SP"))
                .andExpect(jsonPath("$[0].active").value(true));

        Mockito.verify(clubService, Mockito.times(1)).findByNameContainingIgnoreCase(name);
    }
   @Test
    void updateClubSuccessfully() throws Exception {
       ClubRequestDTO clubRequestDTO = new ClubRequestDTO();
       clubRequestDTO.setName("Palmeiras");
       clubRequestDTO.setState("SP");
       clubRequestDTO.setActive(true);
       clubRequestDTO.setStadiumId(1L);

       ClubResponseDTO clubResponseDTO = new ClubResponseDTO(1L, "Sociedade Esportiva Palmeiras",
               "SP", true,"Allianz Park");

       Mockito.when(clubService.updateClub(eq(1L), any())).thenReturn(clubResponseDTO);

       mockMvc.perform(put("/clubs/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(clubRequestDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Sociedade Esportiva Palmeiras"))
               .andExpect(jsonPath("$.state").value("SP"))
               .andExpect(jsonPath("$.stadiumName").value("Allianz Park"));

       }
    @Test
    void deactivateClubSuccessfully() throws Exception {
       Mockito.doNothing().when(clubService).deactivateClub(1L);

       mockMvc.perform(delete("/clubs/1"))
               .andExpect(status().isOk());
   }
   @Test
    void listClubFilters() throws Exception {
        ClubResponseDTO clubResponseDTO = new ClubResponseDTO(1L, "Palmeiras", "SP", true, null);
        List<ClubResponseDTO> content = List.of(clubResponseDTO);

        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<ClubResponseDTO> page = new PageImpl<>(content, pageable, 1);

       Mockito.when(clubService.list(eq("Palmeiras"), eq("SP"),eq( true),any(Pageable.class)))
               .thenReturn(page);

       mockMvc.perform(get("/clubs")
               .param("name", "Palmeiras")
               .param("state", "SP")
               .param("active", "true")
               .param("page", "0")
                       .param("size", "10"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.totalElements").value(1))
               .andExpect(jsonPath("$.content[0].name").value("Palmeiras"));
   }
   @Test
    void return404InvalidId() throws Exception {
       Mockito.when(clubService.findById(99L)).thenThrow(new EntityNotFoundException("Clube não encontrado!"));

       mockMvc.perform(get("/clubs/99")).andExpect(status().isNotFound());
   }

    @Test
    void getClubRankingSuccessfully() throws Exception {
        List<ClubRankingResponseDTO> ranking = List.of(
                new ClubRankingResponseDTO(1L, "Palmeiras", 10L, 6L,
                        2L, 2L, 18L, 10L, 8L, 20L),
                new ClubRankingResponseDTO(2L, "Flamengo", 10L, 5L, 3L,
                        2L, 15L, 12L, 3L, 18L)
        );

        Mockito.when(clubService.getClubRanking()).thenReturn(ranking);

        mockMvc.perform(get("/clubs/ranking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clubName").value("Palmeiras"))
                .andExpect(jsonPath("$[0].points").value(20))
                .andExpect(jsonPath("$[1].clubName").value("Flamengo"))
                .andExpect(jsonPath("$[1].points").value(18));
    }

    @Test
    void getClubStatsSuccessfully() throws Exception {
        ClubStatsResponseDTO stats = new ClubStatsResponseDTO(
                1L, "Palmeiras", 10L, 6L, 2L, 2L,
                18L, 10L, 8L, 20L
        );

        Mockito.when(clubService.getClubStats(1L)).thenReturn(stats);

        mockMvc.perform(get("/clubs/1/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clubName").value("Palmeiras"))
                .andExpect(jsonPath("$.gamesWon").value(6))
                .andExpect(jsonPath("$.goalsScored").value(18))
                .andExpect(jsonPath("$.points").value(20));
    }

    @Test
    void getClubStatsAgainstOpponentsSuccessfully() throws Exception {
        List<ClubStatsResponseDTO> statsList = List.of(
                new ClubStatsResponseDTO(2L, "Flamengo", 4L, 2L, 1L,
                        1L, 7L, 5L, 2L, 7L),
                new ClubStatsResponseDTO(3L, "Corinthians", 3L, 1L, 1L,
                        1L, 4L, 4L, 0L, 4L)
        );

        Mockito.when(clubService.getStatsAgainstOpponents(1L)).thenReturn(statsList);

        mockMvc.perform(get("/clubs/1/stats/opponents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clubName").value("Flamengo"))
                .andExpect(jsonPath("$[0].points").value(7))
                .andExpect(jsonPath("$[1].clubName").value("Corinthians"))
                .andExpect(jsonPath("$[1].points").value(4));
    }


    @Test
    void getHeadToHeadStatsSuccessfully() throws Exception {
        ClubStatsResponseDTO headToHead = new ClubStatsResponseDTO(
                2L, "Flamengo", 5L, 2L, 2L, 1L, 8L,
                6L, 2L, 8L
        );

        Mockito.when(clubService.getHeadToHeadStats(1L, 2L)).thenReturn(headToHead);

        mockMvc.perform(get("/clubs/1/stats/opponent/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clubName").value("Flamengo"))
                .andExpect(jsonPath("$.gamesWon").value(2))
                .andExpect(jsonPath("$.goalsScored").value(8))
                .andExpect(jsonPath("$.points").value(8));
    }

    @Test
    void getClubStatsNotFound() throws Exception {
        Mockito.when(clubService.getClubStats(99L))
                .thenThrow(new EntityNotFoundException("Clube não encontrado!"));

        mockMvc.perform(get("/clubs/99/stats"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findClubByNameReturnEmptyList() throws Exception {
        String name = "Inexistente";

        Mockito.when(clubService.findByNameContainingIgnoreCase(name)).thenReturn(List.of());

        mockMvc.perform(get("/clubs/name/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        Mockito.verify(clubService, Mockito.times(1)).findByNameContainingIgnoreCase(name);
    }
}




