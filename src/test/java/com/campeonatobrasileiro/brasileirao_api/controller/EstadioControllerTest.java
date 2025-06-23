package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.EstadioRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.EstadioResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.EstadioService;
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

@WebMvcTest(EstadioController.class)
public class EstadioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstadioService estadioService;

    @Autowired
    private ObjectMapper objectMapper;

   @Test
        void deveCadastrarEstadioComSucesso() throws Exception {
            EstadioRequestDTO estadioRequestDTO = new EstadioRequestDTO();
            estadioRequestDTO.setNome("Allianz Park");
            estadioRequestDTO.setCidade("São Paulo");
            estadioRequestDTO.setCapacidade(500000);

            EstadioResponseDTO estadioResponseDTO = new EstadioResponseDTO(1L, "Allianz Park", "São Paulo", 50000);

            Mockito.when(estadioService.cadastrarEstadio(Mockito.any())).thenReturn(estadioResponseDTO);

            mockMvc.perform(post("/estadios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estadioRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Allianz Park"))
                .andExpect(jsonPath("$.cidade").value("São Paulo"))
                .andExpect(jsonPath("$.capacidade").value(50000));
    }

    @Test
    void deveBuscarEstadioPorIdComSucesso() throws Exception {
        EstadioResponseDTO estadioResponseDTO = new EstadioResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);
        Mockito.when(estadioService.buscarPorId(1L)).thenReturn(estadioResponseDTO);

        mockMvc.perform(get("/estadios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Maracanã"))
                .andExpect(jsonPath("$.cidade").value("Rio de Janeiro"))
                .andExpect(jsonPath("$.capacidade").value(50000));
    }

    @Test
    void deveAtualizarEstadioComSucesso() throws Exception {
        EstadioRequestDTO estadioRequestDTO = new EstadioRequestDTO();
        estadioRequestDTO.setNome("Allianz Park");
        estadioRequestDTO.setCidade("São Paulo");
        estadioRequestDTO.setCapacidade(50000);

        EstadioResponseDTO estadioResponseDTO = new EstadioResponseDTO(1L, "Neo Quimica Arena", "São Paulo", 50000);

        Mockito.when(estadioService.atualizarEstadio(eq(1L), any())).thenReturn(estadioResponseDTO);

        mockMvc.perform(put("/estadios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estadioRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Neo Quimica Arena"))
                .andExpect(jsonPath("$.cidade").value("São Paulo"))
                .andExpect(jsonPath("$.capacidade").value(50000));

    }

    @Test
    void deveListarEstadiosComPaginacaoComSucesso() throws Exception {
        EstadioResponseDTO estadioResponseDTO = new EstadioResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);
        PageImpl<EstadioResponseDTO> page = new PageImpl<>(List.of(estadioResponseDTO), PageRequest.of(0, 10), 1);

        Mockito.when(estadioService.listar(any(Pageable.class))).thenReturn(page);

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
            EstadioResponseDTO estadioResponseDTO = new EstadioResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);
            PageImpl<EstadioResponseDTO> page = new PageImpl<>(List.of(estadioResponseDTO), PageRequest.of(0, 10), 1);

            Mockito.when(estadioService.listarPorCidade((eq("Rio de Janeiro")),any(Pageable.class))).thenReturn(page);

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
                EstadioResponseDTO estadioResponseDTO = new EstadioResponseDTO(1L, "Maracanã", "Rio de Janeiro", 50000);

                Mockito.when(estadioService.buscarPorNome("Maracanã")).thenReturn(List.of(estadioResponseDTO));

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

                Mockito.when(estadioService.buscarPorNome(eq(nome))).thenReturn(List.of());

                mockMvc.perform(get("/estadios/nome/" + nome))
                        .andExpect(status().isOk())
                        .andExpect(content().json("[]"));

                Mockito.verify(estadioService).buscarPorNome(nome);
            }
            @Test
            void deveRetornar404QuandoBuscarEstadioPorIdInvalido () throws Exception {
                Long idInvalido = 99L;

                Mockito.when(estadioService.buscarPorId(eq(idInvalido))).thenThrow(new EntityNotFoundException("Estádio não encontrado!"));

                mockMvc.perform(get("/estadios/{id}", idInvalido))
                        .andExpect(status().isNotFound());

                Mockito.verify(estadioService).buscarPorId(idInvalido);
            }

        }





