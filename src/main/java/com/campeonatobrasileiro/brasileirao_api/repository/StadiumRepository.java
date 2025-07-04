package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StadiumRepository extends JpaRepository<StadiumEntity, Long> {

    Optional<StadiumEntity> findByNameIgnoreCase(String name);

    Page<StadiumEntity>findAll(Pageable pageable);
    Page<StadiumEntity> findByCityContainingIgnoreCase(String city, Pageable pageable);

}



