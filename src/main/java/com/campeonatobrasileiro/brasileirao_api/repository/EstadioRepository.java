package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.entity.EstadioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadioRepository extends JpaRepository<EstadioEntity, Long> {

    Optional<EstadioEntity> findByNomeIgnoreCase(String nome);
}

