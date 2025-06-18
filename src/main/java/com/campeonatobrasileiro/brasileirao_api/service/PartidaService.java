package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.PartidaRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.PartidaResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubeEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.EstadioEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.PartidaEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubeRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.EstadioRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final ClubeRepository clubeRepository;
    private final EstadioRepository estadioRepository;

    public PartidaService(
            PartidaRepository partidaRepository,
            ClubeRepository clubeRepository,
            EstadioRepository estadioRepository) {
        this.partidaRepository = partidaRepository;
        this.clubeRepository = clubeRepository;
        this.estadioRepository = estadioRepository;
    }

    public PartidaResponseDTO cadastrarPartida(PartidaRequestDTO partidaRequestDTO) {
        ClubeEntity clubeMandante = clubeRepository.findById(partidaRequestDTO.getClubeMandante().getId())
                .orElseThrow(() -> new EntityNotFoundException("Clube mandante não encontrado!"));
        ClubeEntity clubeVisitante = clubeRepository.findById(partidaRequestDTO.getClubeVisitante().getId())
                .orElseThrow(() -> new EntityNotFoundException("Visitante não encontrado!"));
       EstadioEntity estadio = estadioRepository.findById(partidaRequestDTO.getEstadio().getId())
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));

        PartidaEntity partidaEntity = new PartidaEntity();
        partidaEntity.setDataHora(partidaRequestDTO.getDataHora());
        partidaEntity.setClubeMandante(clubeMandante);
        partidaEntity.setClubeVisitante(clubeVisitante);
        partidaEntity.setEstadio(estadio);
        partidaEntity.setGolsMandante(partidaRequestDTO.getGolsMandante());
        partidaEntity.setGolsVisitante(partidaRequestDTO.getGolsVisitante());

        return toResponseDTO(partidaRepository.save(partidaEntity));

    }
         public void deletarPartida(Long Id) {
        PartidaEntity partidaEntity = partidaRepository.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Partida inexistente!"));
        partidaRepository.delete(partidaEntity);
    }
         public PartidaResponseDTO buscarPorId(Long Id) {
        PartidaEntity partidaEntity = partidaRepository.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Partida inexistente!"));
        return toResponseDTO(partidaEntity);
         }

         public Page<PartidaResponseDTO> listarPartidas(Pageable pageable) {
        return partidaRepository.findAll(pageable).map(this::toResponseDTO);
         }

         public Page<PartidaResponseDTO> listarPorClubeMandante(Long Id, Pageable pageable) {
         return partidaRepository.findByClubeMandanteId( Id, pageable ).map(this::toResponseDTO);
         }

         public Page<PartidaResponseDTO> listarPorClubeVisitante(Long Id, Pageable pageable) {
             return partidaRepository.findByClubeVisitanteId(Id, pageable).map(this::toResponseDTO);
         }

         public Page<PartidaResponseDTO> listarPorEstadio(Long Id, Pageable pageable) {
        return partidaRepository.findByEstadioId(Id, pageable).map(this::toResponseDTO);
         }

         private PartidaResponseDTO toResponseDTO(PartidaEntity partidaEntity) {
        return  new PartidaResponseDTO(
                partidaEntity.getId(),
                partidaEntity.getDataHora(),
                partidaEntity.getClubeMandante().getNome(),
                partidaEntity.getClubeVisitante().getNome(),
                partidaEntity.getEstadio().getNome(),
                partidaEntity.getGolsMandante(),
                partidaEntity.getGolsVisitante());



         }

}
