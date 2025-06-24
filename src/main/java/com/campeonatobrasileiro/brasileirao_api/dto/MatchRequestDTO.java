package com.campeonatobrasileiro.brasileirao_api.dto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MatchRequestDTO {

    @NotNull(message = "A data e a hora da partida são obrigatórias!")
    @Future(message = "A data da partida deve ser no futuro!")
    private LocalDateTime dateTime;


    @NotNull(message = "O clube mandante é obrigatório!")
    private Long homeClubId;


    @NotNull(message = "O clube visitante é obrigatório!")
    private Long awayClubId;


    @NotNull(message = "o estádio é obrigatório!")
    private Long stadiumId;
    private Integer homeGoals;
    private Integer awayGoals;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getHomeClubId() {
        return homeClubId;
    }

    public void setHomeClubId(Long homeClubId) {
        this.homeClubId = homeClubId;
    }

    public Long getAwayClubId() {
        return awayClubId;
    }

    public void setAwayClubId(Long awayClubId) {
        this.awayClubId = awayClubId;
    }

    public Long getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(Long stadiumId) {
        this.stadiumId = stadiumId;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Integer getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
    }
}