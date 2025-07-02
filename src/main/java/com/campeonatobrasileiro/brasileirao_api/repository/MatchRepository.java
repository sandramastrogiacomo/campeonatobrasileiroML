package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubHeadToHeadDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubRankingResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubStatsResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.MatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Long> {

    Page<MatchEntity> findByHomeClubId(Long homeClubId, Pageable pageable);

    Page<MatchEntity> findByAwayClubId(Long awayClubId, Pageable pageable);

    Page<MatchEntity> findByStadiumId(Long stadiumId, Pageable pageable);

    Page<MatchEntity> findByStadium_CityContainingIgnoreCase(String city, Pageable pageable);

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

    @Query("""
    SELECT new com.campeonatobrasileiro.brasileirao_api.dto.ClubHeadToHeadDTO(
        CASE
            WHEN m.homeClub.id = :clubId THEN m.awayClub.id
            ELSE m.homeClub.id
        END,
        CASE
            WHEN m.homeClub.id = :clubId THEN m.awayClub.name
            ELSE m.homeClub.name
        END,
        COUNT(m),
        SUM(CASE
            WHEN m.homeClub.id = :clubId AND m.homeGoals > m.awayGoals THEN 1
            WHEN m.awayClub.id = :clubId AND m.awayGoals > m.homeGoals THEN 1
            ELSE 0
        END),
        SUM(CASE
            WHEN m.homeClub.id = :clubId AND m.homeGoals < m.awayGoals THEN 1
            WHEN m.awayClub.id = :clubId AND m.awayGoals < m.homeGoals THEN 1
            ELSE 0
        END),
        SUM(CASE
            WHEN m.homeGoals = m.awayGoals THEN 1
            ELSE 0
        END),
        SUM(CASE
            WHEN m.homeClub.id = :clubId THEN m.homeGoals
            WHEN m.awayClub.id = :clubId THEN m.awayGoals
            ELSE 0
        END),
        SUM(CASE
            WHEN m.homeClub.id = :clubId THEN m.awayGoals
            WHEN m.awayClub.id = :clubId THEN m.homeGoals
            ELSE 0
        END)
    )
    FROM MatchEntity m
    WHERE m.homeClub.id = :clubId OR m.awayClub.id = :clubId
    GROUP BY
        CASE
            WHEN m.homeClub.id = :clubId THEN m.awayClub.id
            ELSE m.homeClub.id
        END,
        CASE
            WHEN m.homeClub.id = :clubId THEN m.awayClub.name
            ELSE m.homeClub.name
        END
""")
    List<ClubHeadToHeadDTO> getHeadToHeadStats(Long clubId);

    @Query("""
    SELECT new com.campeonatobrasileiro.brasileirao_api.dto.ClubRankingResponseDTO(
        c.id,
        c.name,
        COUNT(m),
        SUM(CASE WHEN (m.homeClub.id = c.id AND m.homeGoals > m.awayGoals) OR
                     (m.awayClub.id = c.id AND m.awayGoals > m.homeGoals) THEN 1 ELSE 0 END),
        SUM(CASE WHEN m.homeGoals = m.awayGoals THEN 1 ELSE 0 END),
        SUM(CASE WHEN (m.homeClub.id = c.id AND m.homeGoals < m.awayGoals) OR
                     (m.awayClub.id = c.id AND m.awayGoals < m.homeGoals) THEN 1 ELSE 0 END),
        SUM(CASE WHEN m.homeClub.id = c.id THEN m.homeGoals
                 WHEN m.awayClub.id = c.id THEN m.awayGoals ELSE 0 END),
        SUM(CASE WHEN m.homeClub.id = c.id THEN m.awayGoals
                 WHEN m.awayClub.id = c.id THEN m.homeGoals ELSE 0 END),
        SUM(CASE WHEN m.homeClub.id = c.id THEN m.homeGoals - m.awayGoals
                 WHEN m.awayClub.id = c.id THEN m.awayGoals - m.homeGoals ELSE 0 END),
        SUM(CASE WHEN (m.homeClub.id = c.id AND m.homeGoals > m.awayGoals) OR
                     (m.awayClub.id = c.id AND m.awayGoals > m.homeGoals) THEN 3
                 WHEN m.homeGoals = m.awayGoals THEN 1 ELSE 0 END)
    )
    FROM MatchEntity m
    JOIN ClubEntity c ON c.id = m.homeClub.id OR c.id = m.awayClub.id
    GROUP BY c.id, c.name
    ORDER BY SUM(CASE WHEN (m.homeClub.id = c.id AND m.homeGoals > m.awayGoals) OR
                         (m.awayClub.id = c.id AND m.awayGoals > m.homeGoals) THEN 3
                 WHEN m.homeGoals = m.awayGoals THEN 1 ELSE 0 END) DESC
""")
    List<ClubRankingResponseDTO> getClubRanking();

    @Query("""
      SELECT m FROM MatchEntity m
      WHERE (:startDate IS NULL OR m.matchDate >= :startDate)
         AND (:endDate IS NULL OR m.matchDate <= :endDate)
""")
    Page<MatchEntity> findByMatchDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query ("""
    SELECT m FROM MatchEntity m
    WHERE LOWER (m.stadium.city) LIKE LOWER (COMCAT('%', :city, '%'))
    """)
    Page<MatchEntity> findByStadiumCityContainingIgnoreCase(String city, Pageable pageable);

    @Query("""
    SELECT m FROM MatchEntity m
    WHERE (:clubId IS NULL OR m.homeClub.id = :clubId OR m.awayClub.id = :clubid)
    AND (:stadiumId IS NULL OR m.stadium.id = :stadiumId)
    AND (:startDate IS NULL OR m.matchDate >= :startDate)
    AND (:endDate IS NULL OR m.matchDate <= :endDate)
    """)
    Page<MatchEntity> searchWithFilters (Long clubId, Long stadiumId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("""
        SELECT new com.campeonatobrasileiro.brasileirao_api.dto.ClubHeadToHeadDTO(
            CASE
                WHEN m.homeClub.id = :club1Id THEN m.homeClub.id
                ELSE m.awayClub.id
            END,
            CASE
                WHEN m.homeClub.id = :club1Id THEN m.homeClub.name
                ELSE m.awayClub.name
            END,
            COUNT(m),
            SUM(CASE
                WHEN m.homeClub.id = :club1Id AND m.homeGoals > m.awayGoals THEN 1
                WHEN m.awayClub.id = :club1Id AND m.awayGoals > m.homeGoals THEN 1
                ELSE 0
            END),
            SUM(CASE
                WHEN m.homeClub.id = :club1Id AND m.homeGoals < m.awayGoals THEN 1
                WHEN m.awayClub.id = :club1Id AND m.awayGoals < m.homeGoals THEN 1
                ELSE 0
            END),
            SUM(CASE WHEN m.homeGoals = m.awayGoals THEN 1 ELSE 0 END),
            SUM(CASE
                WHEN m.homeClub.id = :club1Id THEN m.homeGoals
                WHEN m.awayClub.id = :club1Id THEN m.awayGoals
                ELSE 0
            END),
            SUM(CASE
                WHEN m.homeClub.id = :club1Id THEN m.awayGoals
                WHEN m.awayClub.id = :club1Id THEN m.homeGoals
                ELSE 0
            END)
        )
        FROM MatchEntity m
        WHERE (m.homeClub.id = :club1Id AND m.awayClub.id = :club2Id)
           OR (m.homeClub.id = :club2Id AND m.awayClub.id = :club1Id)
        GROUP BY
            CASE WHEN m.homeClub.id = :club1Id THEN m.homeClub.id ELSE m.awayClub.id END,
            CASE WHEN m.homeClub.id = :club1Id THEN m.homeClub.name ELSE m.awayClub.name END
    """)
    List<ClubHeadToHeadDTO> getDirectHeadToHeadStats(Long club1Id, Long club2Id);


    
}
