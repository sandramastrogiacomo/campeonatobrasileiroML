package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.ClubService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableArgumentResolver;
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

   @Autowired
    private MockMvc mockMvc;

   @MockBean
    private ClubService clubService;

   @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PageableArgumentResolver pageableArgumentResolver;


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
}


