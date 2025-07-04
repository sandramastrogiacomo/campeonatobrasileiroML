package com.campeonatobrasileiro.brasileirao_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubHeadToHeadDTO {
    private Long awayClubId;
    private String awayClubName;

    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int gamesDraw;

    private int goalsScored;
    private int goalsConceded;

    private int goalDifference;
    private int points;

    public ClubHeadToHeadDTO(long awayClubId, String awayClubName, long gamesPlayed, long gamesWon, long gamesLost,
           long gamesDraw, long goalsScored, long goalsConceded) {
        this.awayClubId = awayClubId;
        this.awayClubName = awayClubName;
        this.gamesPlayed = (int) gamesPlayed;
        this.gamesWon = (int) gamesWon;
        this.gamesLost = (int) gamesLost;
        this.gamesDraw = (int) gamesDraw;
        this.goalsScored = (int) goalsScored;
        this.goalsConceded = (int) goalsConceded;

        this.goalDifference = 0;
        this.points = 0;

    }

}
