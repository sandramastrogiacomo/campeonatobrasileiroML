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
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;

    public MatchService(MatchRepository matchRepository,
                        ClubRepository clubRepository,
                        StadiumRepository stadiumRepository) {
        this.matchRepository = matchRepository;
        this.clubRepository = clubRepository;
        this.stadiumRepository = stadiumRepository;
    }

    public MatchResponseDTO registerMatch(MatchRequestDTO dto) {
        ClubEntity home = clubRepository.findById(dto.getHomeClubId())
                .orElseThrow(() -> new EntityNotFoundException("Clube mandante não encontrado!"));
        ClubEntity away = clubRepository.findById(dto.getAwayClubId())
                .orElseThrow(() -> new EntityNotFoundException("Clube visitante não encontrado!"));
        StadiumEntity stadium = stadiumRepository.findById(dto.getStadiumId())
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));

        MatchEntity entity = MatchMapperImpl.toMatchEntity(dto);
        entity.setHomeClub(home);
        entity.setAwayClub(away);
        entity.setStadium(stadium);

        return MatchMapperImpl.toResponseDTO(matchRepository.save(entity));
    }

    public void deleteMatch(Long id) {
        MatchEntity match = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partida inexistente!"));
        matchRepository.delete(match);
    }

    public MatchResponseDTO findById(Long id) {
        MatchEntity match = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partida inexistente!"));
        return MatchMapperImpl.toResponseDTO(match);
    }

    public Page<MatchResponseDTO> listMatches(Pageable pageable) {
        return matchRepository.findAll(pageable)
                .map(MatchMapperImpl::toResponseDTO);
    }

    public Page<MatchResponseDTO> listMatchesByHomeClub(Long id, Pageable pageable) {
        return matchRepository.findByHomeClubId(id, pageable)
                .map(MatchMapperImpl::toResponseDTO);
    }

    public Page<MatchResponseDTO> listMatchesByAwayClub(Long id, Pageable pageable) {
        return matchRepository.findByAwayClubId(id, pageable)
                .map(MatchMapperImpl::toResponseDTO);
    }

    public Page<MatchResponseDTO> listMatchesByStadium(Long id, Pageable pageable) {
        return matchRepository.findByStadiumId(id, pageable)
                .map(MatchMapperImpl::toResponseDTO);
    }

    public List<MatchResponseDTO> listMatchesByDate(LocalDate date) {
        return matchRepository.findByMatchDate(date)
                .stream()
                .map(MatchMapperImpl::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<MatchResponseDTO> listMatchesByDateRange(LocalDate start, LocalDate end, Pageable pageable) {
        return matchRepository.findByMatchDateBetween(start, end, pageable)
                .map(MatchMapperImpl::toResponseDTO);
    }

    public Page<MatchResponseDTO> listMatchesByStadiumCity(String city, Pageable pageable) {
        return matchRepository.findByStadiumCityContainingIgnoreCase(city, pageable)
                .map(MatchMapperImpl::toResponseDTO);
    }

    public Page<MatchResponseDTO> searchMatchesWithFilters(Long clubId, Long stadiumId, LocalDate start, LocalDate end, Pageable pageable) {
        return matchRepository.searchWithFilters(clubId, stadiumId, start, end, pageable)
                .map(MatchMapperImpl::toResponseDTO);
    }

    public ClubStatsResponseDTO getClubStats(Long clubId) {
        ClubStatsResponseDTO dto = matchRepository.getClubStats(clubId);
        dto.setGoalsDifference(dto.getGoalsScored() - dto.getGoalsConceded());
        dto.setPoints(dto.getGamesWon() * 3 + dto.getGamesDraw());
        return dto;
    }

    public List<ClubStatsResponseDTO> getStatsAgainstOpponents(Long clubId) {
        return matchRepository.getStatsAgainstOpponents(clubId)
                .stream()
                .map(dto -> {
                    dto.setGoalsDifference(dto.getGoalsScored() - dto.getGoalsConceded());
                    dto.setPoints(dto.getGamesWon() * 3 + dto.getGamesDraw());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ClubHeadToHeadDTO> getHeadToHeadStats(Long clubId, Long opponentId) {
        List<ClubHeadToHeadDTO> list = matchRepository.getDirectHeadToHeadStats(clubId, opponentId);

        for (ClubHeadToHeadDTO dto : list) {
        dto.setGoalDifference(dto.getGoalsScored() - dto.getGoalsConceded());
        dto.setPoints(dto.getGamesWon() * 3 + dto.getGamesDraw());
        }
        return list;
    }


    public List<ClubHeadToHeadDTO> getHeadToHeadStats(Long clubId) {
        List<ClubHeadToHeadDTO> list = matchRepository.getHeadToHeadStats(clubId);
        for (ClubHeadToHeadDTO dto : list) {
            dto.setGoalDifference(dto.getGoalsScored() - dto.getGoalsConceded());
            dto.setPoints(dto.getGamesWon() * 3 + dto.getGamesDraw());
        }
        return list;
    }

    public List<ClubRankingResponseDTO> getRankingStats() {
        return matchRepository.getClubRanking();
    }

    public List<ClubRankingResponseDTO> getClubRankingSortedBy(RankingCriteria criteria) {
        List<ClubRankingResponseDTO> ranking = matchRepository.getClubRanking();
        ranking.sort(switch (criteria) {
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

    public List<ClubHeadToHeadDTO>getDirectHeadToHeadStats(Long club1Id, Long club2Id){
        List<ClubHeadToHeadDTO> list = matchRepository.getDirectHeadToHeadStats(club1Id,club2Id);

        for (ClubHeadToHeadDTO dto : list) {
            dto.setGoalDifference(dto.getGoalsScored() - dto.getGoalsConceded());
            dto.setPoints(dto.getGamesWon() * 3 + dto.getGamesDraw());
        }
        return list;
    }
}