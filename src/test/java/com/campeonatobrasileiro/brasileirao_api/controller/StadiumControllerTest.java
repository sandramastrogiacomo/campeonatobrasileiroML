package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.StadiumRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.StadiumResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.StadiumService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(StadiumController.class)
public class StadiumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StadiumService stadiumService;

    @Autowired
    private ObjectMapper objectMapper;

   @Test
        void registerStadiumSuccessfully() throws Exception {
            StadiumRequestDTO stadiumRequestDTO = new StadiumRequestDTO();
            stadiumRequestDTO.setName("Allianz Park");
            stadiumRequestDTO.setCity("São Paulo");
            stadiumRequestDTO.setCapacity(500000);

            StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Allianz Park", "São Paulo", 50000);

            Mockito.when(stadiumService.registerStadium(Mockito.any())).thenReturn(stadiumResponseDTO);

            mockMvc.perform(post("/stadiums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stadiumRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Allianz Park"))
                .andExpect(jsonPath("$.city").value("São Paulo"))
                .andExpect(jsonPath("$.capacity").value(50000));
    }

    @Test
    void findStadiumByIdSuccessfully() throws Exception {
        StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);
        Mockito.when(stadiumService.findById(1L)).thenReturn(stadiumResponseDTO);

        mockMvc.perform(get("/stadiums/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Maracanã"))
                .andExpect(jsonPath("$.city").value("Rio de Janeiro"))
                .andExpect(jsonPath("$.capacity").value(50000));
    }

    @Test
    void updatStadiumSuccessfully() throws Exception {
        StadiumRequestDTO stadiumRequestDTO = new StadiumRequestDTO();
        stadiumRequestDTO.setName("Allianz Park");
        stadiumRequestDTO.setCity("São Paulo");
        stadiumRequestDTO.setCapacity(50000);

        StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Neo Quimica Arena", "São Paulo", 50000);

        Mockito.when(stadiumService.updateStadium(eq(1L), any())).thenReturn(stadiumResponseDTO);

        mockMvc.perform(put("/estadios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stadiumRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Neo Quimica Arena"))
                .andExpect(jsonPath("$.city").value("São Paulo"))
                .andExpect(jsonPath("$.capacity").value(50000));

    }

    @Test
    void listStadiumsPaginationSuccessfully() throws Exception {
        StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);
        PageImpl<StadiumResponseDTO> page = new PageImpl<>(List.of(stadiumResponseDTO), PageRequest.of(0, 10), 1);

        Mockito.when(stadiumService.list(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/stadiums/pagination")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("Maracanã"))
                .andExpect(jsonPath("$.content[0].city").value("Rio de Janeiro"))
                .andExpect(jsonPath("$.content[0].capacity").value(50000));

    }
        @Test
        void findStadiumByCitySuccessfully() throws Exception {
            StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);
            PageImpl<StadiumResponseDTO> page = new PageImpl<>(List.of(stadiumResponseDTO), PageRequest.of(0, 10), 1);

            Mockito.when(stadiumService.listByCity((eq("Rio de Janeiro")),any(Pageable.class))).thenReturn(page);

            mockMvc.perform(get("/stadiums/find-for-city")
                            .param("city", "Rio de Janeiro")
                            .param("page", "0")
                            .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].id").value(1L))
                    .andExpect(jsonPath("$.content[0].name").value("Maracanã"))
                    .andExpect(jsonPath("$.content[0].city").value("Rio de Janeiro"))
                    .andExpect(jsonPath("$.content[0].capacity").value(50000));
        }

            @Test
            void findStadiumByNameSuccessfully() throws Exception {
                StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);

                Mockito.when(stadiumService.findByName("Maracanã")).thenReturn(List.of(stadiumResponseDTO));

                mockMvc.perform(get("/stadiums/name/Maracanã"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].id").value(1L))
                        .andExpect(jsonPath("$[0].name").value("Maracanã"))
                        .andExpect(jsonPath("$[0].city").value("Rio de Janeiro"))
                        .andExpect(jsonPath("$[0].capacity").value(50000));
            }

            @Test
            void returnEmptyListStadiumNotFound() throws Exception {
                String name = "Estádio não encontrado!";

                Mockito.when(stadiumService.findByName(eq(name))).thenReturn(List.of());

                mockMvc.perform(get("/stadiums/name/" + name))
                        .andExpect(status().isOk())
                        .andExpect(content().json("[]"));

                Mockito.verify(stadiumService).findByName(name);
            }
            @Test
            void return404InvalidId() throws Exception {
                Long invalidId = 99L;

                Mockito.when(stadiumService.findById(eq(invalidId))).thenThrow(new EntityNotFoundException("Estádio não encontrado!"));

                mockMvc.perform(get("/stadiums/{id}", invalidId))
                        .andExpect(status().isNotFound());

                Mockito.verify(stadiumService).findById(invalidId);
            }

        }





