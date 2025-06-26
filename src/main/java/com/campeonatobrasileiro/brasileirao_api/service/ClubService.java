package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.campeonatobrasileiro.brasileirao_api.mapper.ClubMapperImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
    this.clubRepository = clubRepository;
    }

    public ClubResponseDTO createClub(ClubRequestDTO clubRequestDTO) {
        ClubEntity clubEntity = ClubMapperImpl.toEntity(clubRequestDTO);

        clubRepository.save(clubEntity);
        return ClubMapperImpl.toResponseDTO(clubEntity);

    }

    public ClubResponseDTO updateClub(Long id, ClubRequestDTO clubRequestDTO) {
        ClubEntity clubEntity = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado!"));

        clubEntity.setName(clubRequestDTO.getName());
        clubEntity.setState(clubRequestDTO.getState());

        clubRepository.save(clubEntity);
        return ClubMapperImpl.toResponseDTO(clubEntity);
    }

    public void deactivateClub (Long id) {
        ClubEntity clubEntity = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado!"));

        clubEntity.setActive(false);
        clubRepository.save(clubEntity);
    }

    public ClubResponseDTO findById(Long id) {
        ClubEntity clubEntity = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado! "));
        return ClubMapperImpl.toResponseDTO(clubEntity);
    }

    public List<ClubResponseDTO> findByNameContainigIgnoreCase(String name) {
        List<ClubEntity> clubs = clubRepository.findByNameContainingIgnoreCase(name);
        return clubs.stream().map(ClubMapperImpl::toResponseDTO).collect(Collectors.toList());
    }

    public Page<ClubResponseDTO> list(String name, String state, Boolean active, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

       return clubRepository.searchWithFilters(name,state,active, pageable).map(ClubMapperImpl::toResponseDTO);
    }

}