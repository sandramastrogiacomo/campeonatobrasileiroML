package com.campeonatobrasileiro.brasileirao_api.mapper;


import com.campeonatobrasileiro.brasileirao_api.dto.MatchRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.MatchResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.MatchEntity;

public class MatchMapperImpl {

   public  static MatchResponseDTO toResponseDTO(MatchEntity matchEntity) {
       if (matchEntity == null) {
           return null;

   }
    return new MatchResponseDTO (
            matchEntity.getId(),
            matchEntity.getDateTime(),
            matchEntity.getHomeClub().getName(),
            matchEntity.getAwayClub().getName(),
            matchEntity.getStadium().getName(),
            matchEntity.getHomeGoals(),
            matchEntity.getAwayGoals());
   }

   public static MatchEntity toMatchEntity(MatchRequestDTO matchRequestDTO) {

     MatchEntity matchEntity = new MatchEntity();

       matchEntity.setDateTime(matchRequestDTO.getDateTime());
       matchEntity.setHomeGoals(matchRequestDTO.getHomeGoals());
       matchEntity.setAwayGoals(matchRequestDTO.getAwayGoals());
       return matchEntity;
   }

}


