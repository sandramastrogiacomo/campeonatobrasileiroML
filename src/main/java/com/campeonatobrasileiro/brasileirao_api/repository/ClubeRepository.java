package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.entity.ClubeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubeRepository extends JpaRepository<ClubeEntity, Long> {

    Page<ClubeEntity> findByNomeContainingIgnoreCaseAndAtivo(String nome, Boolean ativo, Pageable pageable);

    Page<ClubeEntity> findByAtivo(Boolean ativo, Pageable pageable);

    Page<ClubeEntity> findByEstadoIgnoreCaseAndAtivo(String estado, Boolean ativo, Pageable pageable);

    Page<ClubeEntity> findByEstadoIgnoreCase(String estado, Pageable pageable);

    Page<ClubeEntity> findByNomeContainingIgnoreCaseAndEstadoContainingIgnoreCaseAndAtivoTrue(String nome, String estado, Pageable pageable);
}
