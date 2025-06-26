package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.MatchRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.MatchResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.MatchEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.StadiumRepository;
import com.campeonatobrasileiro.brasileirao_api.repository.MatchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @InjectMocks
    private MatchService matchService;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private StadiumRepository stadiumRepository;

    @Mock
    private ClubRepository clubRepository;

    @Test
    void registerMatchSuccessfully() {

        MatchRequestDTO matchRequestDTO = new MatchRequestDTO();
        matchRequestDTO.setDateTime(LocalDateTime.now());
        matchRequestDTO.setHomeClubId(1L);
        matchRequestDTO.setAwayClubId(2L);
        matchRequestDTO.setStadiumId(3L);
        matchRequestDTO.setHomeGoals(2);
        matchRequestDTO.setAwayGoals(3);

        ClubEntity homeClub = new ClubEntity();
        homeClub.setName("Palmeiras");

        ClubEntity awayClub = new ClubEntity();
        awayClub.setName("Flamengo");

        StadiumEntity stadiumEntity = new StadiumEntity();
        stadiumEntity.setName("Allianz Park");

        MatchEntity matchEntitySalva = new MatchEntity();
        matchEntitySalva.setId(100L);
        matchEntitySalva.setDateTime(LocalDateTime.now());
        matchEntitySalva.setHomeClub(homeClub);
        matchEntitySalva.setAwayClub(awayClub);
        matchEntitySalva.setStadium(stadiumEntity);
        matchEntitySalva.setHomeGoals(2);
        matchEntitySalva.setAwayGoals(1);

        Mockito.when(clubRepository.findById(1L)).thenReturn(Optional.of(homeClub));
        Mockito.when(clubRepository.findById(2L)).thenReturn(Optional.of(awayClub));
        Mockito.when(stadiumRepository.findById(3L)).thenReturn(Optional.of(stadiumEntity));
        Mockito.when(matchRepository.save(Mockito.any(MatchEntity.class))).thenReturn(matchEntitySalva);

        MatchResponseDTO matchResponseDTO = matchService.registerMatch(matchRequestDTO);

        assertNotNull(matchResponseDTO);
        assertEquals("Palmeiras", matchResponseDTO.getHomeClub());
        assertEquals("Flamengo", matchResponseDTO.getAwayClub());
        assertEquals("Allianz Park", matchResponseDTO.getStadium());
        assertEquals(2, matchResponseDTO.getHomeGoals());
        assertEquals(1, matchResponseDTO.getAwayGoals());

        Mockito.verify(clubRepository).findById(1L);
        Mockito.verify(clubRepository).findById(2L);
        Mockito.verify(stadiumRepository).findById(3L);

    }

        @Test
        void findMatchByIdSuccessfully () {
            Long id = 1L;

            ClubEntity homeClub = new ClubEntity();
            homeClub.setName("Palmeiras");

            ClubEntity awayClub = new ClubEntity();
            awayClub.setName("Flamengo");

            StadiumEntity stadiumEntity = new StadiumEntity();
            stadiumEntity.setName("Allianz Park");

            MatchEntity matchEntity = new MatchEntity(1L, LocalDateTime.now(), homeClub,awayClub,
                    stadiumEntity, 2,1);

            Mockito.when(matchRepository.findById(id)).thenReturn(Optional.of(matchEntity));

            MatchResponseDTO matchResponseDTO = matchService.findById(1L);

            assertEquals("Palmeiras", matchResponseDTO.getHomeClub());
            assertEquals("Flamengo", matchResponseDTO.getAwayClub());
            assertEquals("Allianz Park", matchResponseDTO.getStadium());
            assertEquals(2, matchResponseDTO.getHomeGoals());
            assertEquals(1, matchResponseDTO.getAwayGoals());

            Mockito.verify(matchRepository).findById(id);

        }

        @Test
        void deleteMatchByIdSuccessfully () {
        Long id = 1L;

        ClubEntity homeClub = new ClubEntity();
        ClubEntity awayClub = new ClubEntity();
        StadiumEntity stadiumEntity = new StadiumEntity();
        MatchEntity matchEntity = new MatchEntity(1L, LocalDateTime.now(), homeClub,awayClub,
                stadiumEntity, 2,1);

        Mockito.when(matchRepository.findById(id)).thenReturn(Optional.of(matchEntity));

        matchService.deleteMatch(matchEntity.getId());

        Mockito.verify(matchRepository).delete(matchEntity);
        }

        @Test
        void listMatchesPaginatedSuccessfully () {
        Pageable pageable = PageRequest.of(0, 10);

        ClubEntity homeClub = new ClubEntity();
        homeClub.setName("Palmeiras");

        ClubEntity awayClub = new ClubEntity();
        awayClub.setName("Flamengo");

        StadiumEntity stadiumEntity = new StadiumEntity();
        stadiumEntity.setName("Allianz Park");

        MatchEntity matchEntity = new MatchEntity(1L, LocalDateTime.now(), homeClub,awayClub,
                stadiumEntity, 2,1);

            PageImpl<MatchEntity> page = new PageImpl<>(List.of(matchEntity));

            Mockito.when(matchRepository.findAll(pageable)).thenReturn(page);

            Page<MatchResponseDTO> result = matchService.listMatches(pageable);

            assertEquals(1, result.getTotalElements());
            assertEquals("Palmeiras", result.getContent().get(0).getHomeClub());
        }

        @Test
        void listMatchesByHomeClubSuccessfully () {
        Pageable pageable = PageRequest.of(0, 10);
        Long id = 1L;

        ClubEntity homeClub = new ClubEntity();
        homeClub.setName("Palmeiras");
        ClubEntity awayClub = new ClubEntity();
        awayClub.setName("Flamengo");
        StadiumEntity stadiumEntity = new StadiumEntity();
        stadiumEntity.setName("Allianz Park");

        MatchEntity matchEntity = new MatchEntity(1L, LocalDateTime.now(), homeClub,awayClub,
                stadiumEntity, 2,1);

        Mockito.when(matchRepository.findByHomeClubId(1L, pageable)).thenReturn(new PageImpl<>(List.of(matchEntity)));

        Page<MatchResponseDTO> result = matchService.listMatchesByHomeClub(id, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Palmeiras", result.getContent().get(0).getHomeClub());


        Mockito.verify(matchRepository).findByHomeClubId(1L, pageable);

        }

        @Test
        void listMatchesByAwayClubSuccessfully () {
        Pageable pageable = PageRequest.of(0, 10);
        Long id = 2L;

        ClubEntity homeClub = new ClubEntity();
        homeClub.setName("Palmeiras");
        ClubEntity awayClub = new ClubEntity();
        awayClub.setName("Flamengo");

        StadiumEntity stadiumEntity = new StadiumEntity();
        stadiumEntity.setName("Allianz Park");

        MatchEntity matchEntity = new MatchEntity(2L, LocalDateTime.now(), homeClub, awayClub,
                stadiumEntity, 2,1);

        Mockito.when(matchRepository.findByAwayClubId(2L, pageable)).thenReturn(new PageImpl<>(List.of(matchEntity)));

        Page<MatchResponseDTO> result = matchService.listMatchesByAwayClub(2L, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Flamengo", result.getContent().get(0).getAwayClub());

        Mockito.verify(matchRepository).findByAwayClubId(2L, pageable);

    }

     @Test
     void listMatchesByStadiumSuccessfully () {
        Pageable pageable = PageRequest.of(0, 10);
        Long id = 3L;

         ClubEntity homeClub = new ClubEntity();
         homeClub.setName("Palmeiras");

         ClubEntity awayClub = new ClubEntity();
         awayClub.setName("Flamengo");

         StadiumEntity stadiumEntity = new StadiumEntity();
         stadiumEntity.setName("Allianz Park");

         MatchEntity matchEntity = new MatchEntity(1L, LocalDateTime.now(), homeClub,awayClub, stadiumEntity,
                 2,1);

         Mockito.when(matchRepository.findByStadiumId(3L, pageable)).thenReturn(new PageImpl<>(List.of(matchEntity)));

         Page<MatchResponseDTO> result = matchService.listMatchesByStadium(id, pageable);

         assertEquals(1, result.getTotalElements());
         assertEquals("Allianz Park", result.getContent().get(0).getStadium());

         Mockito.verify(matchRepository).findByStadiumId(3L, pageable);

     }

     @Test
    void findMatchByIdException(){
      Mockito.when(matchRepository.findById(999L)).thenReturn(Optional.empty());

         EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
             matchService.findById(999L);
         });

         assertEquals("Partida inexistente!", exception.getMessage());
    }


    }

