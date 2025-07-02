package com.campeonatobrasileiro.brasileirao_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubRankingResponseDTO {
    private Long clubId;
    private String clubName;

    private Long gamesPlayed;
    private Long gamesWon;
    private Long gamesLost;
    private Long gamesDraw;

    private Long goalsScored;
    private Long goalsConceded;
    private Long goalsDifference;
    private Long points;

}
