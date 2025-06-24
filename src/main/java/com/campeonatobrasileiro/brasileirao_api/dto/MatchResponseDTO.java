package com.campeonatobrasileiro.brasileirao_api.dto;

import java.time.LocalDateTime;

public class MatchResponseDTO {

private Long id;
private LocalDateTime dateTime;
private String homeClub;
private String awayClub;
private String stadium;
private Integer homeGoals;
private Integer awayGoals;

    public MatchResponseDTO(Long id, LocalDateTime dateTime, String homeClub, String awayClub, String stadium,
                            Integer homeGoals, Integer awayGoals) {
        this.id = id;
        this.dateTime = dateTime;
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.stadium = stadium;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getHomeClub() {
        return homeClub;
    }

    public void setHomeClub(String homeClub) {
        this.homeClub = homeClub;
    }

    public String getAwayClub() {
        return awayClub;
    }

    public void setAwayClub(String awayClub) {
        this.awayClub = awayClub;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
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

