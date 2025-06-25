package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClubServiceTest {

    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private ClubService clubService;

    private ClubEntity clubEntity;
    private ClubResponseDTO clubResponseDTO;

    @BeforeEach
    public void setUp() {
        clubService = new ClubService(clubRepository);

        clubEntity = new ClubEntity();
        clubResponseDTO = new ClubResponseDTO();
    }

    @Test
    public void findById() {
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setId(1L);
        clubEntity.setName("Palmeiras");
        clubEntity.setState("SP");
        clubEntity.setActive(true);

        when(clubRepository.findById(1L)).thenReturn(Optional.of(clubEntity));

        ClubResponseDTO response = clubService.findById(1L);

        assertEquals("Palmeiras", response.getName());
        assertEquals("SP", response.getState());

    }

    @Test
    public void createClub() {

        ClubRequestDTO clubRequestDTO = new ClubRequestDTO("Palmeiras", "SP");
        ClubEntity clubEntity1 = new ClubEntity(1L, "Palmeiras", "SP", true);

        when(clubRepository.save(any(ClubEntity.class))).thenReturn(clubEntity1);

        ClubResponseDTO response = clubService.createClub(clubRequestDTO);

        assertNotNull(response);
        assertEquals("Palmeiras", response.getName());
        verify(clubRepository, times(1)).save(any(ClubEntity.class));

    }

    @Test
    public void updateClub() {
        ClubRequestDTO clubRequestDTO = new ClubRequestDTO("Sociedade Esportiva Palmeiras", "SP");
        ClubEntity entityExist = new ClubEntity(1L, "Palmeiras", "SP", true);

        when(clubRepository.findById(1L)).thenReturn(Optional.of(entityExist));
        when(clubRepository.save(any(ClubEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ClubResponseDTO response = clubService.updateClub(1L, clubRequestDTO);
        assertNotNull(response);
        assertEquals("Sociedade Esportiva Palmeiras", response.getName());
        assertEquals("SP", response.getState());
        assertEquals(1L, response.getId());

        verify(clubRepository, times(1)).findById(1L);
        verify(clubRepository, times(1)).save(any(ClubEntity.class));

    }

    @Test
    public void deactivateClub() {
        ClubEntity clubExist = new ClubEntity(1L, "Palmeiras", "SP", true);

        when(clubRepository.findById(1L)).thenReturn(Optional.of(clubExist));
        when(clubRepository.save(any(ClubEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        clubService.deactivateClub(1L);
        assertFalse(clubExist.isActive());
        verify(clubRepository, times(1)).findById(1L);
        verify(clubRepository, times(1)).save(any(ClubEntity.class));

    }

    @Test
    public void listClubsByFilters() {
        String name = "Palmeiras";
        String state = "SP";
        Pageable pageable = PageRequest.of(0, 10);

        ClubEntity club1 = new ClubEntity(1L, "Palmeiras", "SP", true);
        ClubEntity club2 = new ClubEntity(2L, "Flamengo", "RJ", true);
        List<ClubEntity> list = Arrays.asList(club1, club2);
        Page<ClubEntity> clubPage = new PageImpl <>(list);

        when(clubRepository.findByFilters("","SP",true,
                PageRequest.of(0, 10, Sort.by("name").ascending())))
                .thenReturn(clubPage);

        Page<ClubResponseDTO> result = clubService.list("","SP", true, 0,10);

        assertEquals(2, result.getTotalElements());
        assertEquals("Palmeiras", result.getContent().get(0).getName());
    }

    @Test
    public void findById_ExceptionInvalidId() {
        Long invalidId = 99L;
        when(clubRepository.findById(invalidId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clubService.findById(invalidId);
        });

        assertTrue(exception.getMessage().contains("Clube não encontrado!"));
    }

    @Test
    public void updateClubExceptionInvalidId() {
        Long invalidId = 99L;
                ClubRequestDTO clubRequestDTO= new ClubRequestDTO("Fluminense","RJ");
        when(clubRepository.findById(invalidId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clubService.updateClub(invalidId, clubRequestDTO);
        });
        assertTrue(exception.getMessage().contains("Clube não encontrado!"));
    }

    @Test
    public void deactivateClubExceptionInvalidId() {
        Long invalidId = 99L;
        when(clubRepository.findById(invalidId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            clubService.deactivateClub(invalidId);

        });
        assertEquals("Clube não encontrado!", exception.getMessage());
    }


}



    

    