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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
    this.clubRepository = clubRepository;
    }

    public ClubResponseDTO createClub(ClubRequestDTO clubRequestDTO) {
        ClubEntity clubEntity = new ClubEntity();

        clubEntity.setName(clubRequestDTO.getName());
        clubEntity.setState(clubRequestDTO.getState());
        clubEntity.setActive(true);

        clubEntity = clubRepository.save(clubEntity);
        return toRespostaDTO(clubEntity);

    }

    public ClubResponseDTO updateClub(Long id, ClubRequestDTO clubRequestDTO) {
        ClubEntity clubEntity = clubRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado!"));

        clubEntity.setName(clubRequestDTO.getName());
        clubEntity.setState(clubRequestDTO.getState());

        clubEntity = clubRepository.save(clubEntity);
        return toRespostaDTO(clubEntity);
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
        return toRespostaDTO(clubEntity);
    }

    public Page<ClubResponseDTO> list(String name, String state, boolean active, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        Page<ClubEntity> clubs = clubRepository.findByFilters(name,state,active, pageable);

       return clubs.map(this::toRespostaDTO);
    }

    private ClubResponseDTO toRespostaDTO(ClubEntity clubEntity) {
        return new ClubResponseDTO(
                clubEntity.getId(),
                clubEntity.getName(),
                clubEntity.getState(),
                clubEntity.isActive());
    }

    public List<ClubResponseDTO> findByName(String name) {
        List<ClubEntity> clubs = clubRepository.findByName(name);
        return clubs.stream().map(this::toRespostaDTO).collect(Collectors.toList());
    }
}