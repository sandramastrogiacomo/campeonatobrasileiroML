package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.entity.EstadioEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadioRepository extends JpaRepository<EstadioEntity, Long> {

    Optional<EstadioEntity> findByNomeIgnoreCase(String nome);

    Page<EstadioEntity>findAll(Pageable pageable);
    Page<EstadioEntity> findByCidadeContainingIgnoreCase(String cidade, Pageable pageable);

}



