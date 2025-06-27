package com.campeonatobrasileiro.brasileirao_api.mapper;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;

public class ClubMapperImpl {

    public static ClubEntity toEntity(ClubRequestDTO clubRequestDTO) {
        ClubEntity clubEntity = new ClubEntity();
        return clubEntity.builder()
                .name(clubRequestDTO.getName())
                .state(clubRequestDTO.getState())
                .stadium(StadiumEntity.builder()
                        .id(clubRequestDTO.getStadiumId())
                        .build())
                .build();

    }

    public static ClubResponseDTO toResponseDTO(ClubEntity clubEntity) {
        return ClubResponseDTO.builder()
                .id(clubEntity.getId())
                .name(clubEntity.getName())
                .state(clubEntity.getState())
                .active(clubEntity.isActive())
                .stadiumName(clubEntity.getStadium() != null ? clubEntity.getStadium().getName() : null)
                .build();
    }
}
