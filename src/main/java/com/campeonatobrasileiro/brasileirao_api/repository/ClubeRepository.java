package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.entity.ClubeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubeRepository extends JpaRepository<ClubeEntity, Long> {

    @Query("SELECT c FROM ClubeEntity c " +
            "WHERE (:nome IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
            "AND (:estado IS NULL OR LOWER(c.estado) LIKE LOWER(CONCAT('%', :estado, '%'))) " +
            "AND (:ativo IS NULL OR c.ativo = :ativo)")
    Page<ClubeEntity> buscarComFiltros(@Param("nome") String nome,
                                       @Param("estado") String estado,
                                       @Param("ativo") Boolean ativo,
                                       Pageable pageable);

    List<ClubeEntity> buscarPorNome(@Param("nome") String nome);
}

