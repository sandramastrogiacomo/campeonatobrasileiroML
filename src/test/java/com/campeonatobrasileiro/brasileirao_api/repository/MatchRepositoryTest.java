package com.campeonatobrasileiro.brasileirao_api.repository;

import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.MatchEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class MatchRepositoryTest {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Test
    void testFindByStadium_CityContainingIgnoreCase() {
        StadiumEntity stadium = StadiumEntity.builder()
                .id(1L)
                .name("Stadium test")
                .city("São Paulo")
                .capacity(20000)
                .build();
        stadiumRepository.save(stadium);

        ClubEntity homeClub = clubRepository.save(ClubEntity.builder().name("Clube A").build());
        ClubEntity awayClub = clubRepository.save(ClubEntity.builder().name("Clube B").build());

        MatchEntity matchEntity = MatchEntity.builder()
                .stadium(stadium)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .dateTime(LocalDateTime.now())
                .matchDate(LocalDate.now())
                .homeGoals(2)
                .awayGoals(1)
                .build();
        matchRepository.save(matchEntity);

        Pageable pageable = PageRequest.of(0, 10);

        Page<MatchEntity> result = matchRepository.findByStadium_CityContainingIgnoreCase(stadium.getCity(), pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals ("São Paulo", result.getContent().get(0).getStadium().getCity());


    }

}
