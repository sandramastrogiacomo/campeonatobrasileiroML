package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.StadiumRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.StadiumResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import com.campeonatobrasileiro.brasileirao_api.mapper.StadiumMapperimpl;
import com.campeonatobrasileiro.brasileirao_api.repository.StadiumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StadiumService {


    private final StadiumRepository stadiumRepository;

    public StadiumService(StadiumRepository stadiumRepository) {
        this.stadiumRepository = stadiumRepository;
    }

    public StadiumResponseDTO registerStadium(StadiumRequestDTO stadiumRequestDTO) {
        StadiumEntity stadiumEntity = new StadiumEntity();

        stadiumEntity.setName(stadiumRequestDTO.getName());
        stadiumEntity.setCapacity(stadiumRequestDTO.getCapacity());
        stadiumEntity.setCity(stadiumRequestDTO.getCity());

        stadiumEntity = stadiumRepository.save(stadiumEntity);
        return StadiumMapperimpl.toResponseDTO(stadiumEntity);
    }

    public StadiumResponseDTO updateStadium(Long id, StadiumRequestDTO stadiumRequestDTO) {
        StadiumEntity stadiumEntity = stadiumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));

        stadiumEntity.setName(stadiumRequestDTO.getName());
        stadiumEntity.setCity(stadiumRequestDTO.getCity());
        stadiumEntity.setCapacity(stadiumRequestDTO.getCapacity());

        stadiumEntity = stadiumRepository.save(stadiumEntity);
        return StadiumMapperimpl.toResponseDTO(stadiumEntity);
    }

    public StadiumResponseDTO findById (Long id) {
        StadiumEntity stadiumEntity = stadiumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));
        return StadiumMapperimpl.toResponseDTO(stadiumEntity);
    }

    public List<StadiumResponseDTO> findByName (String name) {
        Optional<StadiumEntity> entities = stadiumRepository.findByNameIgnoreCase(name);
        return entities.stream().map(StadiumMapperimpl::toResponseDTO).toList();
    }

    public Page<StadiumResponseDTO>list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<StadiumEntity>  stadiums = stadiumRepository.findAll(pageable);
        return stadiums.map(StadiumMapperimpl:: toResponseDTO);
    }

    public Page<StadiumResponseDTO> list(Pageable pageable) {
        return stadiumRepository.findAll(pageable).map(StadiumMapperimpl::toResponseDTO);
    }

    public Page<StadiumResponseDTO> listByCity (String city, Pageable pageable) {
        Page<StadiumEntity> page = stadiumRepository.findByCityContainingIgnoreCase(city, pageable);
        return  page.map(StadiumMapperimpl::toResponseDTO);
    }


    }



