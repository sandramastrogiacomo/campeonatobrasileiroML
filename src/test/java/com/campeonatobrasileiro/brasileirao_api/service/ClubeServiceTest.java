package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubeRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubeResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubeEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubeRepository;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClubeServiceTest {

    @Mock
    private ClubeRepository clubeRepository;

    @InjectMocks
    private ClubeService clubeService;

    private ClubeEntity clubeEntity;
    private ClubeResponseDTO clubeResponseDTO;

    @BeforeEach
    public void setUp() {
        clubeRepository = mock(ClubeRepository.class);
        clubeService = new ClubeService(clubeRepository);

        clubeEntity = new ClubeEntity();
        clubeResponseDTO = new ClubeResponseDTO();
    }

    @Test
    public void buscarPorId() {
        ClubeEntity clubeEntity1 = new ClubeEntity();
        clubeEntity.setId(1L);
        clubeEntity.setNome("Palmeiras");
        clubeEntity.setEstado("SP");
        clubeEntity.setAtivo(true);

        when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeEntity));

        ClubeResponseDTO resposta = clubeService.buscarPorId(1L);

        assertEquals("Palmeiras", resposta.getNome());
        assertEquals("SP", resposta.getEstado());

    }

    @Test
    public void cadastrarClube() {

        ClubeRequestDTO clubeRequestDTO = new ClubeRequestDTO("Palmeiras", "SP");
        ClubeEntity clubeEntity1 = new ClubeEntity(1L, "Palmeiras", "SP", true);

        when(clubeRepository.save(any(ClubeEntity.class))).thenReturn(clubeEntity1);

        ClubeResponseDTO resposta = clubeService.cadastrarClube(clubeRequestDTO);

        assertNotNull(resposta);
        assertEquals("Palmeiras", resposta.getNome());
        verify(clubeRepository, times(1)).save(any(ClubeEntity.class));

    }

    @Test
    public void atualizarClube() {
        ClubeRequestDTO clubeRequestDTO = new ClubeRequestDTO("Sociedade Esportiva Palmeiras", "SP");
        ClubeEntity entityExistente = new ClubeEntity(1L, "Palmeiras", "SP", true);

        when(clubeRepository.findById(1L)).thenReturn(Optional.of(entityExistente));
        when(clubeRepository.save(any(ClubeEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ClubeResponseDTO resposta = clubeService.atualizarClube(1L, clubeRequestDTO);
        assertNotNull(resposta);
        assertEquals("Sociedade Esportiva Palmeiras", resposta.getNome());
        assertEquals("SP", resposta.getEstado());
        assertEquals(1L, resposta.getId());

        verify(clubeRepository, times(1)).findById(1L);
        verify(clubeRepository, times(1)).save(any(ClubeEntity.class));

    }

    @Test
    public void inativarClube() {
        ClubeEntity clubeExistente = new ClubeEntity(1L, "Palmeiras", "SP", true);

        when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeExistente));
        when(clubeRepository.save(any(ClubeEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        clubeService.inativarClube(1L);
        assertFalse(clubeExistente.isAtivo());
        verify(clubeRepository, times(1)).findById(1L);
        verify(clubeRepository, times(1)).save(any(ClubeEntity.class));

    }

    @Test
    public void listarClubes() {
        String nome = "Palmeiras";
        String estado = "SP";
        Pageable pageable = PageRequest.of(0, 10);

        ClubeEntity clube1 = new ClubeEntity(1L, "Palmeiras", "SP", true);
        ClubeEntity clube2 = new ClubeEntity(2L, "Palmeiras B", "SP", true);
        List<ClubeEntity> clubes = Arrays.asList(clube1, clube2);
        Page<ClubeEntity> clubePage = new PageImpl(clubes, pageable, clubes.size());

        when(clubeRepository.findByNomeContainingIgnoreCaseAndEstadoContainingIgnoreCaseAndAtivoTrue(nome, estado, pageable))
                .thenReturn(clubePage);

        Page<ClubeEntity> resultado = clubeService.listarClubes(nome, estado, pageable);

        assertEquals(2, resultado.getContent().size());
        assertEquals("Palmeiras", resultado.getContent().get(0).getNome());
        assertEquals("SP", resultado.getContent().get(0).getEstado());
        assertEquals(1L, resultado.getContent().get(0).getId());
        verify(clubeRepository, times(1))
                .findByNomeContainingIgnoreCaseAndEstadoContainingIgnoreCaseAndAtivoTrue (nome, estado, pageable);

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
                ClubeRequestDTO clubeRequestDTO= new ClubeRequestDTO("Fut fut","MG");
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

    @Test
    public void listarClubeFiltrarNomeEAtivo() {
        String nome = "Palmeiras";
        Boolean ativo = true;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
        List<ClubeEntity> clubes =List.of (new ClubeEntity(1L, "Palmeiras", "SP", true));
        Page<ClubeEntity> clubePage = new PageImpl(clubes);

        when (clubeRepository.findByNomeContainingIgnoreCaseAndAtivo (nome, ativo, pageable))
                .thenReturn(clubePage);
        Page<ClubeResponseDTO> resultado = clubeService.listar (nome, null, ativo,0,10 );
        assertEquals(1, resultado.getContent().size());
        assertEquals("Palmeiras", resultado.getContent().get(0).getNome());
    }

    @Test
    public void listaClubeFiltrarEstadoEAtivo() {
        String estado = "SP";
        Boolean ativo = true;
        Pageable pageable = PageRequest.of(0, 10);
        List<ClubeEntity> clubes = List.of (new ClubeEntity(2L, "Palmeiras", "SP", true));
        Page<ClubeEntity> clubePage = new PageImpl(clubes);

        when(clubeRepository.findByEstadoIgnoreCaseAndAtivo (eq("SP"), eq(true),
                any( Pageable.class))).thenReturn(clubePage);

        Page<ClubeResponseDTO> resultado = clubeService.listar (null, estado, ativo,0,10 );

        assertEquals(1, resultado.getContent().size());
        assertEquals("SP", clubePage.getContent().get(0).getEstado());
    }
}



    

    