package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.StadiumRepository;
import com.campeonatobrasileiro.brasileirao_api.service.ClubService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ClubController.class)
public class ClubControllerTest {

   @Autowired
    private MockMvc mockMvc;

   @MockBean
    private ClubService clubService;

   @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StadiumRepository stadiumRepository;
    @Autowired
    private ClubRepository clubRepository;

    @Test
   void createClubSuccessfully() throws Exception {
       ClubRequestDTO clubRequestDTO = new ClubRequestDTO();
       clubRequestDTO.setName("Palmeiras");
       clubRequestDTO.setState("SP");
       clubRequestDTO.setActive(true);
       clubRequestDTO.setStadiumId(1L);

       StadiumEntity stadiumEntity = new StadiumEntity();
       stadiumEntity.setId(1L);
       stadiumEntity.setName("Allianz Park");

       ClubEntity clubEntity = new ClubEntity();
       clubEntity.setId(1L);
       clubEntity.setName("Palmeiras");
       clubEntity.setState("SP");
       clubEntity.setActive(true);
       clubEntity.setStadium(stadiumEntity);

       Mockito.when(stadiumRepository.findById(1L)).thenReturn(Optional.of(stadiumEntity));
       Mockito.when(clubRepository.save(any(ClubEntity.class))).thenReturn(clubEntity);

       ClubResponseDTO clubResponseDTO = clubService.createClub(clubRequestDTO);

       assertNotNull(clubResponseDTO);
       assertEquals("Palmeiras", clubResponseDTO.getName());
       assertEquals("SP", clubResponseDTO.getState());
       assertEquals("Allianz Park", clubResponseDTO.getStadiumName());

       Mockito.verify(clubRepository, Mockito.times(1)).save(any(ClubEntity.class));

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

        Mockito.when(clubService.findByNameContainigIgnoreCase(name)).thenReturn(list);

        mockMvc.perform(get("/clubs/name/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Palmeiras"))
                .andExpect(jsonPath("$[0].state").value("SP"))
                .andExpect(jsonPath("$[0].active").value(true));

        Mockito.verify(clubService, Mockito.times(1)).findByNameContainigIgnoreCase(name);
    }
   @Test
    void updateClubSuccessfully() throws Exception {
       ClubRequestDTO clubRequestDTO = new ClubRequestDTO();
       clubRequestDTO.setName("Palmeiras");
       clubRequestDTO.setState("SP");
       clubRequestDTO.setActive(true);

       ClubResponseDTO clubResponseDTO = new ClubResponseDTO(1L, "Sociedade Esportiva Palmeiras", "SP", true, null);

       Mockito.when(clubService.updateClub(eq(1L), any())).thenReturn(clubResponseDTO);

       mockMvc.perform(put("/clubs/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(clubRequestDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Sociedade Esportiva Palmeiras"))
               .andExpect(jsonPath("$.state").value("SP"));
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

       PageImpl<ClubResponseDTO> page = new PageImpl<>(List.of(clubResponseDTO));

       Mockito.when(clubService.list("Palmeiras", "SP", true,0,10 ))
               .thenReturn(page);

       mockMvc.perform(get("/clubs")
               .param("name", "Palmeiras")
               .param("state", "SP")
               .param("active", "true")
               .param("page", "0")
                       .param("size", "10"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].name").value("Palmeiras"));
   }
   @Test
    void return404InvalidId() throws Exception {
       Mockito.when(clubService.findById(99L)).thenThrow(new EntityNotFoundException("Clube não encontrado!"));

       mockMvc.perform(get("/clubs/99")).andExpect(status().isNotFound());
   }
}


