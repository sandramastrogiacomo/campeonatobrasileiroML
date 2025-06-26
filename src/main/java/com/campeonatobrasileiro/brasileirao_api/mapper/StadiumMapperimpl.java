package com.campeonatobrasileiro.brasileirao_api.mapper;

import com.campeonatobrasileiro.brasileirao_api.dto.StadiumRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.StadiumResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;

public class StadiumMapperimpl {

    public static StadiumResponseDTO toResponseDTO (StadiumEntity stadiumEntity) {
        return new StadiumResponseDTO(
                stadiumEntity.getId(),
                stadiumEntity.getName(),
                stadiumEntity.getCity(),
                stadiumEntity.getCapacity());
    }
     public static StadiumEntity toEntity(StadiumRequestDTO  stadiumRequestDTO) {
        StadiumEntity stadiumEntity = new StadiumEntity();
        stadiumEntity.setName(stadiumEntity.getName());
        stadiumEntity.setCity(stadiumEntity.getCity());
        stadiumEntity.setCapacity(stadiumEntity.getCapacity());
        return stadiumEntity;

    }
}
