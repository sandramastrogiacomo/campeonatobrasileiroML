package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.model.EstadioModel;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadioRepository extends JpaRepository<EstadioModel, Long> {

    Optional<EstadioModel> findByNomeIgnoreCase(String nome);
}

