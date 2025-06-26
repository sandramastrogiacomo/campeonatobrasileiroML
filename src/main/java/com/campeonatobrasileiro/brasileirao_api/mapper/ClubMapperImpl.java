package com.campeonatobrasileiro.brasileirao_api.mapper;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;

public class ClubMapperImpl {

    public static ClubEntity toEntity(ClubRequestDTO clubRequestDTO) {
        ClubEntity clubEntity = new ClubEntity();

        clubEntity.setName(clubRequestDTO.getName());
        clubEntity.setState(clubRequestDTO.getState());
        clubEntity.setActive(clubRequestDTO.getActive());
        return clubEntity;
    }

    public static ClubResponseDTO toResponseDTO(ClubEntity clubEntity) {
        return new ClubResponseDTO(clubEntity.getId(), clubEntity.getName(), clubEntity.getState(), clubEntity.isActive());
    }
}
