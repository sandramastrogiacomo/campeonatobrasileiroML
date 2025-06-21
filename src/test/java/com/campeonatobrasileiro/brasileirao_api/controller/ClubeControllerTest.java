package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubeRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubeResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.ClubeService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
       ClubeRequestDTO clubeRequestDTO = new ClubeRequestDTO("Palmeiras", "SP");
       ClubeResponseDTO clubeResponseDTO = new ClubeResponseDTO(1L, "Palmeiras", "SP", true);

       Mockito.when(clubeService.cadastrarClube(any())).thenReturn(clubeResponseDTO);

       mockMvc.perform(post("/clubes")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(clubeRequestDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.nome").value("Palmeiras"))
               .andExpect(jsonPath("$.estado").value("SP"));
   }
    @Test
    void deveBuscarClubePorIdComSucesso() throws Exception {
       ClubeResponseDTO clubeResponseDTO = new ClubeResponseDTO(1L, "Palmeiras", "SP", true);

       Mockito.when(clubeService.buscarPorId(1L)).thenReturn(clubeResponseDTO);

       mockMvc.perform(get("/clubes/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nome").value("Palmeiras"))
               .andExpect(jsonPath("$.estado").value("SP"));
    }

    @Test
    void deveBuscarClubePorNomeComSucesso() throws Exception {
       String nome = "Palmeiras";

       ClubeResponseDTO  clubeResponseDTO= new ClubeResponseDTO(1L, "Palmeiras", "SP", true);
        List<ClubeResponseDTO> lista = List.of(clubeResponseDTO);

        Mockito.when(clubeService.buscarPorNome(nome)).thenReturn(lista);

        mockMvc.perform(get("/clubes/nome/{nome}", nome))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Palmeiras"))
                .andExpect(jsonPath("$[0].estado").value("SP"))
                .andExpect(jsonPath("$[0].ativo").value(true));

        Mockito.verify(clubeService, Mockito.times(1)).buscarPorNome(nome);
    }
   @Test
    void deveAtualizarClubeComSucesso() throws Exception {
       ClubeRequestDTO clubeRequestDTO = new ClubeRequestDTO("Sociedade Esportiva Palmeiras", "SP");
       ClubeResponseDTO clubeResponseDTO = new ClubeResponseDTO(1L, "Sociedade Esportiva Palmeiras", "SP", true);

       Mockito.when(clubeService.atualizarClube(eq(1L), any())).thenReturn(clubeResponseDTO);

       mockMvc.perform(put("/clubes/1")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(clubeRequestDTO)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nome").value("Sociedade Esportiva Palmeiras"))
               .andExpect(jsonPath("$.estado").value("SP"));
       }
    @Test
    void deveInativarClubeComSucesso() throws Exception {
       Mockito.doNothing().when(clubeService).inativarClube(1L);

       mockMvc.perform(delete("/clubes/1"))
               .andExpect(status().isOk());
   }
   @Test
    void deveListarClubeComFiltro() throws Exception {
       ClubeResponseDTO clubeResponseDTO = new ClubeResponseDTO(1L, "Palmeiras", "SP", true);

       PageImpl<ClubeResponseDTO> pagina = new PageImpl<>(List.of(clubeResponseDTO));

       Mockito.when(clubeService.listar("Palmeiras", "SP", true,0,10 ))
               .thenReturn(pagina);

       mockMvc.perform(get("/clubes")
               .param("nome", "Palmeiras")
               .param("estado", "SP")
               .param("ativo", "true")
               .param("page", "0")
                       .param("size", "10"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content[0].nome").value("Palmeiras"));
   }
   @Test
    void deveRetornar404SeIdInvalido() throws Exception {
       Mockito.when(clubeService.buscarPorId(99L)).thenThrow(new EntityNotFoundException("Clube não encontrado!"));

       mockMvc.perform(get("/clubes/99")).andExpect(status().isNotFound());
   }
}


