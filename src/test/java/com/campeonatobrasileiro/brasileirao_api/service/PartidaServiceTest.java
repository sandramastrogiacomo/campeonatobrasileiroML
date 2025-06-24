package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.MatchRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.MatchResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.EstadioEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.PartidaEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.EstadioRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class PartidaServiceTest {

    @InjectMocks
    private PartidaService partidaService;

    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private EstadioRepository estadioRepository;

    @Mock
    private ClubRepository clubeRepository;

    @Test
    void cadastrarPartidaComSucesso() {

        MatchRequestDTO matchRequestDTO = new MatchRequestDTO();
        matchRequestDTO.setDataHora(LocalDateTime.now());
        matchRequestDTO.setClubeMandanteId(1L);
        matchRequestDTO.setClubeVisitanteId(2L);
        matchRequestDTO.setEstadioId(3L);
        matchRequestDTO.setGolsMandante(2);
        matchRequestDTO.setGolsVisitante(3);

        ClubEntity clubeMandante = new ClubEntity(1L, "Palmeiras", "SP", true);
        ClubEntity clubeVisitante = new ClubEntity(2L, "Flamengo", "RJ", true);
        EstadioEntity estadioEntity = new EstadioEntity(3L, "Allianz Park", "São Paulo", 50000);

        PartidaEntity partidaEntitySalva = new PartidaEntity();
        partidaEntitySalva.setId(100L);
        partidaEntitySalva.setDataHora(LocalDateTime.now());
        partidaEntitySalva.setClubeMandante(clubeMandante);
        partidaEntitySalva.setClubeVisitante(clubeVisitante);
        partidaEntitySalva.setEstadio(estadioEntity);
        partidaEntitySalva.setGolsMandante(2);
        partidaEntitySalva.setGolsVisitante(1);

        Mockito.when(clubeRepository.findById(1L)).thenReturn(Optional.of(clubeMandante));
        Mockito.when(clubeRepository.findById(2L)).thenReturn(Optional.of(clubeVisitante));
        Mockito.when(estadioRepository.findById(3L)).thenReturn(Optional.of(estadioEntity));
        Mockito.when(partidaRepository.save(Mockito.any(PartidaEntity.class))).thenReturn(partidaEntitySalva);

        MatchResponseDTO matchResponseDTO = partidaService.cadastrarPartida(matchRequestDTO);

        assertNotNull(matchResponseDTO);
        assertEquals("Palmeiras", matchResponseDTO.getClubeMandante());
        assertEquals("Flamengo", matchResponseDTO.getClubeVisitante());
        assertEquals("Allianz Park", matchResponseDTO.getEstadio());
        assertEquals(2, matchResponseDTO.getGolsMandante());
        assertEquals(1, matchResponseDTO.getGolsVisitante());

        Mockito.verify(clubeRepository).findById(1L);
        Mockito.verify(clubeRepository).findById(2L);
        Mockito.verify(estadioRepository).findById(3L);

    }

        @Test
        void buscarPartidaPorIdComSucesso () {
            Long Id = 1L;

            ClubEntity clubeMandante = new ClubEntity(1L, "Palmeiras", "SP", true);
            ClubEntity clubeVisitante = new ClubEntity(2L, "Flamengo", "RJ", true);
            EstadioEntity estadioEntity = new EstadioEntity(3L, "Allianz Park", "São Paulo", 50000);

            PartidaEntity partidaEntity = new PartidaEntity(1L, LocalDateTime.now(), clubeMandante,clubeVisitante, estadioEntity, 2,1);

            Mockito.when(partidaRepository.findById(Id)).thenReturn(Optional.of(partidaEntity));

            MatchResponseDTO matchResponseDTO = partidaService.buscarPorId(1L);

            assertEquals("Palmeiras", matchResponseDTO.getClubeMandante());
            assertEquals("Flamengo", matchResponseDTO.getClubeVisitante());
            assertEquals("Allianz Park", matchResponseDTO.getEstadio());
            assertEquals(2, matchResponseDTO.getGolsMandante());
            assertEquals(1, matchResponseDTO.getGolsVisitante());

            Mockito.verify(partidaRepository).findById(Id);

        }

        @Test
        void deletarPartidaPorIdComSucesso () {
        Long Id = 1L;

        ClubEntity clubeMandante = new ClubEntity(1L, "Palmeiras", "SP", true);
        ClubEntity clubeVisitante = new ClubEntity(2L, "Flamengo", "RJ", true);
        EstadioEntity estadioEntity = new EstadioEntity(1L, "Allianz Park", "São Paulo",50000);
        PartidaEntity partidaEntity = new PartidaEntity(1L, LocalDateTime.now(), clubeMandante,clubeVisitante, estadioEntity, 2,1);

        Mockito.when(partidaRepository.findById(Id)).thenReturn(Optional.of(partidaEntity));

        partidaService.deletarPartida(partidaEntity.getId());

        Mockito.verify(partidaRepository).delete(partidaEntity);
        }

        @Test
        void listarTodasPartidasComPaginacao (){
        Pageable pageable = PageRequest.of(0, 10);

        ClubEntity clubeMandante = new ClubEntity(1L, "Palmeiras", "SP", true);
        ClubEntity clubeVisitante = new ClubEntity(2L, "Flamengo", "RJ", true);
        EstadioEntity estadioEntity = new EstadioEntity(3L,"Allianz Park", "São Paulo", 50000);

        PartidaEntity partidaEntity = new PartidaEntity(1L, LocalDateTime.now(), clubeMandante,clubeVisitante, estadioEntity, 2,1);

            PageImpl<PartidaEntity> page = new PageImpl<>(List.of(partidaEntity));

            Mockito.when(partidaRepository.findAll(pageable)).thenReturn(page);

            Page<MatchResponseDTO> resultado = partidaService.listarPartidas(pageable);

            assertEquals(1, resultado.getTotalElements());
            assertEquals("Palmeiras", resultado.getContent().get(0).getClubeMandante());
        }

        @Test
        void listarPartidasPorClubeMandante(){
        Pageable pageable = PageRequest.of(0, 10);
        Long id = 1L;

        ClubEntity clubeMandante = new ClubEntity(1L, "Palmeiras", "SP", true);
        ClubEntity clubeVisitante = new ClubEntity(2L, "Flamengo", "RJ", true);
        EstadioEntity estadioEntity = new EstadioEntity(3L,"Allianz Park", "São Paulo",50000);

        PartidaEntity partidaEntity = new PartidaEntity(1L, LocalDateTime.now(), clubeMandante,clubeVisitante, estadioEntity, 2,1);

        Mockito.when(partidaRepository.findByClubeMandanteId(1L, pageable)).thenReturn(new PageImpl<>(List.of(partidaEntity)));

        Page<MatchResponseDTO> resultado = partidaService.listarPorClubeMandante(id, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Palmeiras", resultado.getContent().get(0).getClubeMandante());


        Mockito.verify(partidaRepository).findByClubeMandanteId(1L, pageable);

        }

        @Test
        void listarPartidasPorClubeVisitante(){
        Pageable pageable = PageRequest.of(0, 10);
        Long id = 2L;

        ClubEntity clubeMandante = new ClubEntity(1L, "Palmeiras", "SP", true);
        ClubEntity clubeVisitante = new ClubEntity(2L, "Flamengo", "RJ", true);
        EstadioEntity estadioEntity = new EstadioEntity(3L,"Allianz Park", "São Paulo",50000);

        PartidaEntity partidaEntity = new PartidaEntity(2L, LocalDateTime.now(), clubeMandante,clubeVisitante, estadioEntity, 2,1);

        Mockito.when(partidaRepository.findByClubeVisitanteId(2L, pageable)).thenReturn(new PageImpl<>(List.of(partidaEntity)));

        Page<MatchResponseDTO> resultado = partidaService.listarPorClubeVisitante(2L, pageable);

        assertEquals(1, resultado.getTotalElements());
        assertEquals("Flamengo", resultado.getContent().get(0).getClubeVisitante());

        Mockito.verify(partidaRepository).findByClubeVisitanteId(2L, pageable);

    }

     @Test
     void listarPartidasPorEstadio(){
        Pageable pageable = PageRequest.of(0, 10);
        Long id = 3L;

         ClubEntity clubeMandante = new ClubEntity(1L, "Palmeiras", "SP", true);
         ClubEntity clubeVisitante = new ClubEntity(2L, "Flamengo", "RJ", true);
         EstadioEntity estadioEntity = new EstadioEntity(3L,"Allianz Park", "São Paulo",50000);

         PartidaEntity partidaEntity = new PartidaEntity(1L, LocalDateTime.now(), clubeMandante,clubeVisitante, estadioEntity, 2,1);

         Mockito.when(partidaRepository.findByEstadioId(3L, pageable)).thenReturn(new PageImpl<>(List.of(partidaEntity)));

         Page<MatchResponseDTO> resultado = partidaService.listarPorEstadio(id, pageable);

         assertEquals(1, resultado.getTotalElements());
         assertEquals("Allianz Park", resultado.getContent().get(0).getEstadio());

         Mockito.verify(partidaRepository).findByEstadioId(3L, pageable);

     }

     @Test
    void buscarPartidaporIdDeveLancarExcecao(){
      Mockito.when(partidaRepository.findById(999L)).thenReturn(Optional.empty());

         EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
             partidaService.buscarPorId(999L);
         });

         assertEquals("Partida inexistente!", exception.getMessage());
    }


    }

