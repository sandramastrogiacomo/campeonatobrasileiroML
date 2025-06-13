package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.model.ClubeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubeRepository extends JpaRepository<ClubeModel, Long> {

    Page<ClubeModel> findByNomeContainingIgnoreCaseAndAtivo(String nome, Boolean ativo, Pageable pageable);

    Page<ClubeModel> findByAtivo(Boolean ativo, Pageable pageable);

    Page<ClubeModel> findByEstadoIgnoreCaseAndAtivo(String estado, Boolean ativo, Pageable pageable);

    Page<ClubeModel> findByEstadoIgnoreCase(String estado, Pageable pageable);
}
