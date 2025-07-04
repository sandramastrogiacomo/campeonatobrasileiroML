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
    private Long gamesPlayed;
    private Long gamesWon;
    private Long gamesLost;
    private Long gamesDraw;
    private Long goalsScored;
    private Long goalsConceded;


    private Long goalsDifference;
    private Long points;

    public ClubStatsResponseDTO(Long clubId, String clubName, Long gamesPlayed, Long gamesWon, Long gamesLost,
                                Long gamesDraw, Long goalsScored, Long goalsConceded) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.gamesDraw = gamesDraw;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
    }


}
