package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.MatchRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.MatchResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.MatchEntity;
import com.campeonatobrasileiro.brasileirao_api.mapper.MatchMapperImpl;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.StadiumRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;

    public MatchService(
            MatchRepository matchRepository,
            ClubRepository clubRepository,
            StadiumRepository stadiumRepository) {
        this.matchRepository = matchRepository;
        this.clubRepository = clubRepository;
        this.stadiumRepository = stadiumRepository;
    }

    public MatchResponseDTO registerMatch(MatchRequestDTO matchRequestDTO) {
        ClubEntity homeClub = clubRepository.findById(matchRequestDTO.getHomeClubId())
                .orElseThrow(() -> new EntityNotFoundException("Clube mandante não encontrado!"));
        ClubEntity awayClub = clubRepository.findById(matchRequestDTO.getAwayClubId())
                .orElseThrow(() -> new EntityNotFoundException("Visitante não encontrado!"));
       StadiumEntity stadium
               = stadiumRepository.findById(matchRequestDTO.getStadiumId())
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));

        MatchEntity matchEntity = MatchMapperImpl.toMatchEntity(matchRequestDTO);

        matchEntity.setHomeClub(homeClub);
        matchEntity.setAwayClub(awayClub);
        matchEntity.setStadium(stadium);

        return MatchMapperImpl.toResponseDTO(matchRepository.save(matchEntity));

    }
         public void deleteMatch(Long id) {
        MatchEntity matchEntity = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partida inexistente!"));
        matchRepository.delete(matchEntity);
    }
         public MatchResponseDTO findById(Long id) {
        MatchEntity matchEntity = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partida inexistente!"));
        return MatchMapperImpl.toResponseDTO(matchEntity);
         }

         public Page<MatchResponseDTO> listMatches(Pageable pageable) {
        return matchRepository.findAll(pageable).map(MatchMapperImpl::toResponseDTO);
         }

         public Page<MatchResponseDTO> listMatchesByHomeClub(Long id, Pageable pageable) {
         return matchRepository.findByHomeClubId( id, pageable ).map(MatchMapperImpl::toResponseDTO);
         }

         public Page<MatchResponseDTO> listMatchesByAwayClub(Long id, Pageable pageable) {
             return matchRepository.findByAwayClubId(id, pageable).map(MatchMapperImpl::toResponseDTO);
         }

         public Page<MatchResponseDTO> listMatchesByStadium(Long id, Pageable pageable) {
        return matchRepository.findByStadiumId(id, pageable).map(MatchMapperImpl::toResponseDTO);
         }

         }


