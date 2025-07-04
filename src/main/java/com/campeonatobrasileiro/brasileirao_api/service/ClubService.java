package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRankingResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubStatsResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import com.campeonatobrasileiro.brasileirao_api.enums.RankingCriteria;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.StadiumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.campeonatobrasileiro.brasileirao_api.mapper.ClubMapperImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;
    private final MatchService matchService;

    public ClubService(ClubRepository clubRepository, StadiumRepository stadiumRepository, MatchService matchService) {
        this.clubRepository = clubRepository;
        this.stadiumRepository = stadiumRepository;
        this.matchService = matchService;
    }

    public ClubResponseDTO createClub(ClubRequestDTO clubRequestDTO) {
        StadiumEntity stadiumEntity = stadiumRepository.findById(clubRequestDTO.getStadiumId())
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));;

                ClubEntity clubEntity = ClubMapperImpl.toEntity(clubRequestDTO);
                clubEntity.setStadium(stadiumEntity);

                ClubEntity savedClubEntity = clubRepository.save(clubEntity);
                return ClubMapperImpl.toResponseDTO(savedClubEntity);
    }

    public ClubResponseDTO updateClub(Long id, ClubRequestDTO clubRequestDTO) {
        ClubEntity clubEntityExist = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado!"));

        StadiumEntity stadiumEntity = stadiumRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));

        clubEntityExist.setName(clubRequestDTO.getName());
        clubEntityExist.setState(clubRequestDTO.getState());
        clubEntityExist.setStadium(stadiumEntity);

        ClubEntity clubEntityUpdated = clubRepository.save(clubEntityExist);
        return ClubMapperImpl.toResponseDTO(clubEntityUpdated);
    }

    public void deactivateClub (Long id) {
        ClubEntity clubEntity = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado!"));

        clubEntity.setActive(false);
        clubRepository.save(clubEntity);
    }

    public ClubResponseDTO findById(Long id) {
        ClubEntity clubEntity = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado! "));
        return ClubMapperImpl.toResponseDTO(clubEntity);
    }

    public List<ClubResponseDTO> findByNameContainingIgnoreCase(String name) {
        List<ClubEntity> clubs = clubRepository.findByNameContainingIgnoreCase(name);
        return clubs.stream().map(ClubMapperImpl::toResponseDTO).collect(Collectors.toList());
    }

    public Page<ClubResponseDTO> list(String name, String state, Boolean active, Pageable pageable) {
        Page<ClubEntity> page = clubRepository.searchWithFilters(name, state, active, pageable);

        return page.map(ClubMapperImpl::toResponseDTO);
    }

    public List<ClubRankingResponseDTO>getClubRanking(){
        return List.of();
    }

    public ClubStatsResponseDTO getClubStats(Long id) {
        return null;
    }

    public List<ClubStatsResponseDTO>getStatsAgainstOpponents(Long id){
        return List.of();
    }

    public ClubStatsResponseDTO getHeadToHeadStats(Long clubId, Long opponentId){
        return null;
    }

    public List<ClubRankingResponseDTO> getClubRankingSortedBy(RankingCriteria rankingCriteria){
        return matchService.getClubRankingSortedBy(rankingCriteria);
    }

}