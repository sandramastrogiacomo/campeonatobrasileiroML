package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.entity.MatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchEntity, Long> {

    Page<MatchEntity> findByHomeClubId (Long homeClubId, Pageable pageable);
    Page<MatchEntity> findByAwayClubId (Long awayClubId, Pageable pageable);
    Page<MatchEntity> findByStadiumId (Long stadiumId, Pageable pageable);


}
