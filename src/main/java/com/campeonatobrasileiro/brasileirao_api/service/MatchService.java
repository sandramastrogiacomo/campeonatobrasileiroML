package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.*;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.MatchEntity;
import com.campeonatobrasileiro.brasileirao_api.enums.RankingCriteria;
import com.campeonatobrasileiro.brasileirao_api.mapper.MatchMapperImpl;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.StadiumRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;
    private Long stadiumId;

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
        return matchRepository.findByHomeClubId(id, pageable).map(MatchMapperImpl::toResponseDTO);
    }

    public Page<MatchResponseDTO> listMatchesByAwayClub(Long id, Pageable pageable) {
        return matchRepository.findByAwayClubId(id, pageable).map(MatchMapperImpl::toResponseDTO);
    }

    public Page<MatchResponseDTO> listMatchesByStadium(Long id, Pageable pageable) {
        return matchRepository.findByStadiumId(id, pageable).map(MatchMapperImpl::toResponseDTO);
    }

    public ClubStatsResponseDTO getClubStats(Long clubId) {
        ClubStatsResponseDTO clubStatsResponseDTO = matchRepository.getClubStats(clubId);

        clubStatsResponseDTO.setGoalsDifference(clubStatsResponseDTO.getGoalsDifference() - clubStatsResponseDTO.getGoalsConceded());

        clubStatsResponseDTO.setPoints(clubStatsResponseDTO.getGamesWon() * 3 + clubStatsResponseDTO.getGamesDraw());

        return clubStatsResponseDTO;
    }

    public List<ClubHeadToHeadDTO> getHeadToHeadStats(Long clubId) {
        List<ClubHeadToHeadDTO> headToHeadList = matchRepository.getHeadToHeadStats(clubId);

        for (ClubHeadToHeadDTO headToHeadDTO : headToHeadList) {
            int goalsDifference = headToHeadDTO.getGoalsScored() - headToHeadDTO.getGoalsConceded();
            int points = headToHeadDTO.getGamesWon() * 3 + headToHeadDTO.getGamesDraw();

            headToHeadDTO.setGoalDifference(goalsDifference);
            headToHeadDTO.setPoints(points);
        }
        return headToHeadList;
    }

    public List<ClubRankingResponseDTO> getRankingStats() {
        return matchRepository.getClubRanking();
    }

    public Page<MatchResponseDTO> findMatchesByDateRanger(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return matchRepository.findByMatchDateBetween(startDate, endDate, pageable).map(MatchMapperImpl::toResponseDTO);
    }

    public Page<MatchResponseDTO> listMatchesByStadiumCity(String city, Pageable pageable) {
        return matchRepository.findByStadiumCityContainingIgnoreCase(city, pageable).map(MatchMapperImpl::toResponseDTO);
    }

    public Page<MatchResponseDTO> searchMatchesWithFilters(Long clubId, Long stadiumId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return matchRepository.searchWithFilters(clubId, this.stadiumId, startDate, endDate, pageable)
                .map(MatchMapperImpl::toResponseDTO);
    }

    public List<ClubHeadToHeadDTO> getDirectHeadToHeadStats(Long club1Id, Long club2Id) {
        return matchRepository.getDirectHeadToHeadStats(club1Id, club2Id);
    }

    public List<ClubRankingResponseDTO> getClubRankingSortedBy(RankingCriteria sortBy) {
        List<ClubRankingResponseDTO> ranking = matchRepository.getClubRanking();

        ranking.sort(switch (sortBy) {
            case POINTS -> Comparator.comparing(ClubRankingResponseDTO::getPoints).reversed();
            case GAMES_PLAYED -> Comparator.comparing(ClubRankingResponseDTO::getGamesPlayed).reversed();
            case GAMES_WON -> Comparator.comparing(ClubRankingResponseDTO::getGamesWon).reversed();
            case GAMES_DRAW -> Comparator.comparing(ClubRankingResponseDTO::getGamesDraw).reversed();
            case GAMES_LOST -> Comparator.comparing(ClubRankingResponseDTO::getGamesLost);
            case GOALS_SCORED -> Comparator.comparing(ClubRankingResponseDTO::getGoalsScored).reversed();
            case GOALS_CONCEDED -> Comparator.comparing(ClubRankingResponseDTO::getGoalsConceded);
            case GOALS_DIFFERENCE -> Comparator.comparing(ClubRankingResponseDTO::getGoalsDifference).reversed();
        });

        return ranking;
    }
}



