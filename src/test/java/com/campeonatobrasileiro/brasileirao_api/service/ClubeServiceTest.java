package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubeDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubeRespostaDTO;
import com.campeonatobrasileiro.brasileirao_api.model.ClubeModel;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
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

    private ClubeModel clubeModel;
    private ClubeRespostaDTO clubeRespostaDTO;

    @BeforeEach
    public void setUp() {
        clubeRepository = mock(ClubeRepository.class);
        clubeService = new ClubeService(clubeRepository);

        clubeModel = new ClubeModel();
        clubeRespostaDTO = new ClubeRespostaDTO();
    }

    @Test
    public void buscarPorId() {
        ClubeModel clubeModel = new ClubeModel();
        clubeModel.setId(1L);
        clubeModel.setNome("Palmeiras");
        clubeModel.setEstado("SP");
        clubeModel.setAtivo(true);

        when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeModel));

        ClubeRespostaDTO resposta = clubeService.buscarPorId(1L);

        assertEquals("Palmeiras", resposta.getNome());
        assertEquals("SP", resposta.getEstado());

    }

    @Test
    public void cadastrarClube() {

        ClubeDTO clubeDTO = new ClubeDTO("Palmeiras", "SP");
        ClubeModel clubeModel = new ClubeModel(1L, "Palmeiras", "SP", true);

        when(clubeRepository.save(any(ClubeModel.class))).thenReturn(clubeModel);

        ClubeRespostaDTO resposta = clubeService.cadastrarClube(clubeDTO);

        assertNotNull(resposta);
        assertEquals("Palmeiras", resposta.getNome());
        verify(clubeRepository, times(1)).save(any(ClubeModel.class));

    }

    @Test
    public void atualizarClube() {
        ClubeDTO clubeDTO = new ClubeDTO("Sociedade Esportiva Palmeiras", "SP");
        ClubeModel modelExistente = new ClubeModel(1L, "Palmeiras", "SP", true);

        when(clubeRepository.findById(1L)).thenReturn(Optional.of(modelExistente));
        when(clubeRepository.save(any(ClubeModel.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ClubeRespostaDTO resposta = clubeService.atualizarClube(1L, clubeDTO);
        assertNotNull(resposta);
        assertEquals("Sociedade Esportiva Palmeiras", resposta.getNome());
        assertEquals("SP", resposta.getEstado());
        assertEquals(1L, resposta.getId());

        verify(clubeRepository, times(1)).findById(1L);
        verify(clubeRepository, times(1)).save(any(ClubeModel.class));

    }

    @Test
    public void inativarClube() {
        ClubeModel clubeExistente = new ClubeModel(1L, "Palmeiras", "SP", true);

        when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeExistente));
        when(clubeRepository.save(any(ClubeModel.class))).thenAnswer(invocation -> invocation.getArgument(0));
        clubeService.inativarClube(1L);
        assertFalse(clubeExistente.isAtivo());
        verify(clubeRepository, times(1)).findById(1L);
        verify(clubeRepository, times(1)).save(any(ClubeModel.class));

    }

    @Test
    public void listarClubes() {
        String nome = "Palmeiras";
        String estado = "SP";
        String ativo = "true";
        Pageable pageable = PageRequest.of(0, 10);

        ClubeModel clube1 = new ClubeModel(1L, "Palmeiras", "SP", true);
        ClubeModel clube2 = new ClubeModel(2L, "Palmeiras B", "SP", true);
        List<ClubeModel> clubes = Arrays.asList(clube1, clube2);
        Page<ClubeModel> clubePage = new PageImpl(clubes, pageable, 0);

        when(clubeRepository.findByNomeContainingIgnoreCaseAndAtivo("Palmeiras", true, pageable))
                .thenReturn(clubePage);

        Page<ClubeModel> resultado = clubeService.listarClubes("Palmeiras", "SP", pageable);

        assertEquals("Palmeiras", resultado.getContent().get(0).getNome());
        assertEquals("SP", resultado.getContent().get(0).getEstado());
        assertEquals(1L, resultado.getContent().get(0).getId());
        verify(clubeRepository, times(1))
                .findByEstadoIgnoreCaseAndAtivo("SP", true, pageable);

    }
}



    

    