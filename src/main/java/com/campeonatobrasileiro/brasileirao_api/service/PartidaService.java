package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.MatchRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.MatchResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.MatchEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.StadiumRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {

    private final MatchRepository matchRepository;
    private final ClubRepository clubeRepository;
    private final StadiumRepository stadiumRepository;

    public PartidaService(
            MatchRepository matchRepository,
            ClubRepository clubeRepository,
            StadiumRepository stadiumRepository) {
        this.matchRepository = matchRepository;
        this.clubeRepository = clubeRepository;
        this.stadiumRepository = stadiumRepository;
    }

    public MatchResponseDTO cadastrarPartida(MatchRequestDTO matchRequestDTO) {
        ClubEntity clubeMandante = clubeRepository.findById(matchRequestDTO.getClubeMandanteId())
                .orElseThrow(() -> new EntityNotFoundException("Clube mandante não encontrado!"));
        ClubEntity clubeVisitante = clubeRepository.findById(matchRequestDTO.getClubeVisitanteId())
                .orElseThrow(() -> new EntityNotFoundException("Visitante não encontrado!"));
       StadiumEntity estadio = stadiumRepository.findById(matchRequestDTO.getEstadioId())
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));

        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setDataHora(matchRequestDTO.getDataHora());
        matchEntity.setClubeMandante(clubeMandante);
        matchEntity.setClubeVisitante(clubeVisitante);
        matchEntity.setEstadio(estadio);
        matchEntity.setGolsMandante(matchRequestDTO.getGolsMandante());
        matchEntity.setGolsVisitante(matchRequestDTO.getGolsVisitante());

        return toResponseDTO(matchRepository.save(matchEntity));

    }
         public void deletarPartida(Long Id) {
        MatchEntity matchEntity = matchRepository.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Partida inexistente!"));
        matchRepository.delete(matchEntity);
    }
         public MatchResponseDTO buscarPorId(Long Id) {
        MatchEntity matchEntity = matchRepository.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Partida inexistente!"));
        return toResponseDTO(matchEntity);
         }

         public Page<MatchResponseDTO> listarPartidas(Pageable pageable) {
        return matchRepository.findAll(pageable).map(this::toResponseDTO);
         }

         public Page<MatchResponseDTO> listarPorClubeMandante(Long Id, Pageable pageable) {
         return matchRepository.findByClubeMandanteId( Id, pageable ).map(this::toResponseDTO);
         }

         public Page<MatchResponseDTO> listarPorClubeVisitante(Long Id, Pageable pageable) {
             return matchRepository.findByClubeVisitanteId(Id, pageable).map(this::toResponseDTO);
         }

         public Page<MatchResponseDTO> listarPorEstadio(Long Id, Pageable pageable) {
        return matchRepository.findByEstadioId(Id, pageable).map(this::toResponseDTO);
         }

         private MatchResponseDTO toResponseDTO(MatchEntity matchEntity) {
        return  new MatchResponseDTO(
                matchEntity.getId(),
                matchEntity.getDataHora(),
                matchEntity.getClubeMandante().getNome(),
                matchEntity.getClubeVisitante().getNome(),
                matchEntity.getEstadio().getNome(),
                matchEntity.getGolsMandante(),
                matchEntity.getGolsVisitante());



         }

}
