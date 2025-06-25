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
public class EstadioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StadiumService stadiumService;

    @Autowired
    private ObjectMapper objectMapper;

   @Test
        void deveCadastrarEstadioComSucesso() throws Exception {
            StadiumRequestDTO stadiumRequestDTO = new StadiumRequestDTO();
            stadiumRequestDTO.setNome("Allianz Park");
            stadiumRequestDTO.setCidade("São Paulo");
            stadiumRequestDTO.setCapacidade(500000);

            StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Allianz Park", "São Paulo", 50000);

            Mockito.when(stadiumService.cadastrarEstadio(Mockito.any())).thenReturn(stadiumResponseDTO);

            mockMvc.perform(post("/estadios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stadiumRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Allianz Park"))
                .andExpect(jsonPath("$.cidade").value("São Paulo"))
                .andExpect(jsonPath("$.capacidade").value(50000));
    }

    @Test
    void deveBuscarEstadioPorIdComSucesso() throws Exception {
        StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);
        Mockito.when(stadiumService.buscarPorId(1L)).thenReturn(stadiumResponseDTO);

        mockMvc.perform(get("/estadios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Maracanã"))
                .andExpect(jsonPath("$.cidade").value("Rio de Janeiro"))
                .andExpect(jsonPath("$.capacidade").value(50000));
    }

    @Test
    void deveAtualizarEstadioComSucesso() throws Exception {
        StadiumRequestDTO stadiumRequestDTO = new StadiumRequestDTO();
        stadiumRequestDTO.setNome("Allianz Park");
        stadiumRequestDTO.setCidade("São Paulo");
        stadiumRequestDTO.setCapacidade(50000);

        StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Neo Quimica Arena", "São Paulo", 50000);

        Mockito.when(stadiumService.atualizarEstadio(eq(1L), any())).thenReturn(stadiumResponseDTO);

        mockMvc.perform(put("/estadios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stadiumRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Neo Quimica Arena"))
                .andExpect(jsonPath("$.cidade").value("São Paulo"))
                .andExpect(jsonPath("$.capacidade").value(50000));

    }

    @Test
    void deveListarEstadiosComPaginacaoComSucesso() throws Exception {
        StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);
        PageImpl<StadiumResponseDTO> page = new PageImpl<>(List.of(stadiumResponseDTO), PageRequest.of(0, 10), 1);

        Mockito.when(stadiumService.listar(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/estadios")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].nome").value("Maracanã"))
                .andExpect(jsonPath("$.content[0].cidade").value("Rio de Janeiro"))
                .andExpect(jsonPath("$.content[0].capacidade").value(50000));

    }
        @Test
        void deveBuscarEstadioPorCidadeComSucesso() throws Exception {
            StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);
            PageImpl<StadiumResponseDTO> page = new PageImpl<>(List.of(stadiumResponseDTO), PageRequest.of(0, 10), 1);

            Mockito.when(stadiumService.listarPorCidade((eq("Rio de Janeiro")),any(Pageable.class))).thenReturn(page);

            mockMvc.perform(get("/estadios/buscar-por-cidade")
                            .param("cidade", "Rio de Janeiro")
                            .param("page", "0")
                            .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].id").value(1L))
                    .andExpect(jsonPath("$.content[0].nome").value("Maracanã"))
                    .andExpect(jsonPath("$.content[0].cidade").value("Rio de Janeiro"))
                    .andExpect(jsonPath("$.content[0].capacidade").value(50000));
        }

            @Test
            void deveBuscarEstadioPorNomeComSucesso () throws Exception {
                StadiumResponseDTO stadiumResponseDTO = new StadiumResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);

                Mockito.when(stadiumService.buscarPorNome("Maracanã")).thenReturn(List.of(stadiumResponseDTO));

                mockMvc.perform(get("/estadios/nome/Maracanã"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].id").value(1L))
                        .andExpect(jsonPath("$[0].nome").value("Maracanã"))
                        .andExpect(jsonPath("$[0].cidade").value("Rio de Janeiro"))
                        .andExpect(jsonPath("$[0].capacidade").value(50000));
            }

            @Test
            void deveretornarListaVaziaQuandoEstadioNaoEncontradoPorNome () throws Exception {
                String nome = "Estádio não encontrado!";

                Mockito.when(stadiumService.buscarPorNome(eq(nome))).thenReturn(List.of());

                mockMvc.perform(get("/estadios/nome/" + nome))
                        .andExpect(status().isOk())
                        .andExpect(content().json("[]"));

                Mockito.verify(stadiumService).buscarPorNome(nome);
            }
            @Test
            void deveRetornar404QuandoBuscarEstadioPorIdInvalido () throws Exception {
                Long idInvalido = 99L;

                Mockito.when(stadiumService.buscarPorId(eq(idInvalido))).thenThrow(new EntityNotFoundException("Estádio não encontrado!"));

                mockMvc.perform(get("/estadios/{id}", idInvalido))
                        .andExpect(status().isNotFound());

                Mockito.verify(stadiumService).buscarPorId(idInvalido);
            }

        }





