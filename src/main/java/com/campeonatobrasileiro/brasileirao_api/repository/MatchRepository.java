package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubStatsResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.MatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {

    Page<MatchEntity> findByHomeClubId (Long homeClubId, Pageable pageable);
    Page<MatchEntity> findByAwayClubId (Long awayClubId, Pageable pageable);
    Page<MatchEntity> findByStadiumId (Long stadiumId, Pageable pageable);

    @Query("""
    SELECT m FROM MatchEntity m
    WHERE m.homeClub.id = :clubId OR m.awayClub.id = :clubId
""")
    Page<MatchEntity> findAllByClubId(Long clubId, Pageable pageable);

    @Query("""
    SELECT 
        COUNT(m) AS totalGames,
        SUM(CASE 
            WHEN m.homeClub.id = :clubId AND m.homeGoals > m.awayGoals THEN 1
            WHEN m.awayClub.id = :clubId AND m.awayGoals > m.homeGoals THEN 1
            ELSE 0 END) AS gamesWon,
        SUM(CASE 
            WHEN m.homeGoals = m.awayGoals THEN 1
            ELSE 0 END) AS gamesDraw,
        SUM(CASE 
            WHEN m.homeClub.id = :clubId AND m.homeGoals < m.awayGoals THEN 1
            WHEN m.awayClub.id = :clubId AND m.awayGoals < m.homeGoals THEN 1
            ELSE 0 END) AS gamesLost,
        SUM(CASE 
            WHEN m.homeClub.id = :clubId THEN m.homeGoals
            WHEN m.awayClub.id = :clubId THEN m.awayGoals
            ELSE 0 END) AS goalsScored,
        SUM(CASE 
            WHEN m.homeClub.id = :clubId THEN m.awayGoals
            WHEN m.awayClub.id = :clubId THEN m.homeGoals
            ELSE 0 END) AS goalsConceded
    FROM MatchEntity m
    WHERE m.homeClub.id = :clubId OR m.awayClub.id = :clubId
""")
    ClubStatsResponseDTO getClubStats(Long clubId);
}
