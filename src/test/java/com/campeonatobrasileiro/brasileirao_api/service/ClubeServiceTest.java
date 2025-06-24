package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClubeServiceTest {

    @Mock
    private ClubRepository clubeRepository;

    @InjectMocks
    private ClubService clubeService;

    private ClubEntity clubeEntity;
    private ClubResponseDTO clubeResponseDTO;

    @BeforeEach
    public void setUp() {
        clubeService = new ClubService(clubeRepository);

        clubeEntity = new ClubEntity();
        clubeResponseDTO = new ClubResponseDTO();
    }

    @Test
    public void buscarPorId() {
        ClubEntity clubeEntity1 = new ClubEntity();
        clubeEntity.setId(1L);
        clubeEntity.setNome("Palmeiras");
        clubeEntity.setEstado("SP");
        clubeEntity.setAtivo(true);

        when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeEntity));

        ClubResponseDTO resposta = clubeService.buscarPorId(1L);

        assertEquals("Palmeiras", resposta.getNome());
        assertEquals("SP", resposta.getEstado());

    }

    @Test
    public void cadastrarClube() {

        ClubRequestDTO clubeRequestDTO = new ClubRequestDTO("Palmeiras", "SP");
        ClubEntity clubeEntity1 = new ClubEntity(1L, "Palmeiras", "SP", true);

        when(clubeRepository.save(any(ClubEntity.class))).thenReturn(clubeEntity1);

        ClubResponseDTO resposta = clubeService.cadastrarClube(clubeRequestDTO);

        assertNotNull(resposta);
        assertEquals("Palmeiras", resposta.getNome());
        verify(clubeRepository, times(1)).save(any(ClubEntity.class));

    }

    @Test
    public void atualizarClube() {
        ClubRequestDTO clubeRequestDTO = new ClubRequestDTO("Sociedade Esportiva Palmeiras", "SP");
        ClubEntity entityExistente = new ClubEntity(1L, "Palmeiras", "SP", true);

        when(clubeRepository.findById(1L)).thenReturn(Optional.of(entityExistente));
        when(clubeRepository.save(any(ClubEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ClubResponseDTO resposta = clubeService.atualizarClube(1L, clubeRequestDTO);
        assertNotNull(resposta);
        assertEquals("Sociedade Esportiva Palmeiras", resposta.getNome());
        assertEquals("SP", resposta.getEstado());
        assertEquals(1L, resposta.getId());

        verify(clubeRepository, times(1)).findById(1L);
        verify(clubeRepository, times(1)).save(any(ClubEntity.class));

    }

    @Test
    public void inativarClube() {
        ClubEntity clubeExistente = new ClubEntity(1L, "Palmeiras", "SP", true);

        when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeExistente));
        when(clubeRepository.save(any(ClubEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        clubeService.inativarClube(1L);
        assertFalse(clubeExistente.isAtivo());
        verify(clubeRepository, times(1)).findById(1L);
        verify(clubeRepository, times(1)).save(any(ClubEntity.class));

    }

    @Test
    public void listarClubesComFiltros() {
        String nome = "Palmeiras";
        String estado = "SP";
        Pageable pageable = PageRequest.of(0, 10);

        ClubEntity clube1 = new ClubEntity(1L, "Palmeiras", "SP", true);
        ClubEntity clube2 = new ClubEntity(2L, "Flamengo", "RJ", true);
        List<ClubEntity> lista = Arrays.asList(clube1, clube2);
        Page<ClubEntity> clubePage = new PageImpl <>(lista);

        when(clubeRepository.buscarComFiltros("","SP",true,
                PageRequest.of(0, 10, Sort.by("nome").ascending())))
                .thenReturn(clubePage);

        Page<ClubResponseDTO> resultado = clubeService.listar("","SP", true, 0,10);

        assertEquals(2, resultado.getTotalElements());
        assertEquals("Palmeiras", resultado.getContent().get(0).getNome());
    }

    @Test
    public void buscarPorId_LancarExceptionSeIdInvalido() {
        Long idInvalido = 99L;
        when(clubeRepository.findById(idInvalido)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clubeService.buscarPorId(idInvalido);
        });

        assertTrue(exception.getMessage().contains("Clube não encontrado!"));
    }

    @Test
    public void atualizarClubeLancarExceptionSeIdInvalido() {
        Long idInvalido = 99L;
                ClubRequestDTO clubeRequestDTO= new ClubRequestDTO("Fut fut","MG");
        when(clubeRepository.findById(idInvalido)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clubeService.atualizarClube(idInvalido, clubeRequestDTO);
        });
        assertTrue(exception.getMessage().contains("Clube não encontrado!"));
    }

    @Test
    public void inativarClubeLancarExceptionSeIdInvalido() {
        Long idInvalido = 99L;
        when(clubeRepository.findById(idInvalido)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clubeService.inativarClube(idInvalido);

        });
        assertEquals("Clube não encontrado!", exception.getMessage());
    }


}



    

    