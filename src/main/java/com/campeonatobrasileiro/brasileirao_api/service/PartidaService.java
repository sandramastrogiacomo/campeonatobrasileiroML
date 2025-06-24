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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final ClubRepository clubeRepository;
    private final EstadioRepository estadioRepository;

    public PartidaService(
            PartidaRepository partidaRepository,
            ClubRepository clubeRepository,
            EstadioRepository estadioRepository) {
        this.partidaRepository = partidaRepository;
        this.clubeRepository = clubeRepository;
        this.estadioRepository = estadioRepository;
    }

    public MatchResponseDTO cadastrarPartida(MatchRequestDTO matchRequestDTO) {
        ClubEntity clubeMandante = clubeRepository.findById(matchRequestDTO.getClubeMandanteId())
                .orElseThrow(() -> new EntityNotFoundException("Clube mandante não encontrado!"));
        ClubEntity clubeVisitante = clubeRepository.findById(matchRequestDTO.getClubeVisitanteId())
                .orElseThrow(() -> new EntityNotFoundException("Visitante não encontrado!"));
       EstadioEntity estadio = estadioRepository.findById(matchRequestDTO.getEstadioId())
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));

        PartidaEntity partidaEntity = new PartidaEntity();
        partidaEntity.setDataHora(matchRequestDTO.getDataHora());
        partidaEntity.setClubeMandante(clubeMandante);
        partidaEntity.setClubeVisitante(clubeVisitante);
        partidaEntity.setEstadio(estadio);
        partidaEntity.setGolsMandante(matchRequestDTO.getGolsMandante());
        partidaEntity.setGolsVisitante(matchRequestDTO.getGolsVisitante());

        return toResponseDTO(partidaRepository.save(partidaEntity));

    }
         public void deletarPartida(Long Id) {
        PartidaEntity partidaEntity = partidaRepository.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Partida inexistente!"));
        partidaRepository.delete(partidaEntity);
    }
         public MatchResponseDTO buscarPorId(Long Id) {
        PartidaEntity partidaEntity = partidaRepository.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Partida inexistente!"));
        return toResponseDTO(partidaEntity);
         }

         public Page<MatchResponseDTO> listarPartidas(Pageable pageable) {
        return partidaRepository.findAll(pageable).map(this::toResponseDTO);
         }

         public Page<MatchResponseDTO> listarPorClubeMandante(Long Id, Pageable pageable) {
         return partidaRepository.findByClubeMandanteId( Id, pageable ).map(this::toResponseDTO);
         }

         public Page<MatchResponseDTO> listarPorClubeVisitante(Long Id, Pageable pageable) {
             return partidaRepository.findByClubeVisitanteId(Id, pageable).map(this::toResponseDTO);
         }

         public Page<MatchResponseDTO> listarPorEstadio(Long Id, Pageable pageable) {
        return partidaRepository.findByEstadioId(Id, pageable).map(this::toResponseDTO);
         }

         private MatchResponseDTO toResponseDTO(PartidaEntity partidaEntity) {
        return  new MatchResponseDTO(
                partidaEntity.getId(),
                partidaEntity.getDataHora(),
                partidaEntity.getClubeMandante().getNome(),
                partidaEntity.getClubeVisitante().getNome(),
                partidaEntity.getEstadio().getNome(),
                partidaEntity.getGolsMandante(),
                partidaEntity.getGolsVisitante());



         }

}
