package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity, Long> {

    @Query("SELECT c FROM ClubEntity c " +
            "WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:state IS NULL OR LOWER(c.state) LIKE LOWER(CONCAT('%', :state, '%'))) " +
            "AND (:active IS NULL OR c.active = :active)")
    Page<ClubEntity> searchWithFilters(@Param("name") String name,
                                      @Param("state") String state,
                                      @Param("active") Boolean active,
                                      Pageable pageable);

    List<ClubEntity> findByNameContainingIgnoreCase(String name);
}

