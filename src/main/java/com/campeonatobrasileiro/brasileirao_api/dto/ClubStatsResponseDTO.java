package com.campeonatobrasileiro.brasileirao_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubStatsResponseDTO {
    private Long clubId;
    private String clubName;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int gamesDraw;
    private int goalsScored;
    private int goalsConceded;
    private int goalsDifference;
    private int points;
    
}
