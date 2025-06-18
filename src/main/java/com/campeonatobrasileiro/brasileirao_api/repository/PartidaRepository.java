package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.entity.PartidaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository  extends JpaRepository<PartidaEntity, Long> {

    Page<PartidaEntity> findByClubeMandanteId (Long clubeMandanteId, Pageable pageable);
    Page<PartidaEntity> findByClubeVisitanteId (Long clubeVisitanteId, Pageable pageable);
    Page<PartidaEntity> findByEstadioId (Long estadioId, Pageable pageable);

}
