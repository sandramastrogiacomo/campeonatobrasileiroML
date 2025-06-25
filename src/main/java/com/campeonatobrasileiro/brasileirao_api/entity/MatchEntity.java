package com.campeonatobrasileiro.brasileirao_api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "partidas")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @ManyToOne (fetch =  FetchType.LAZY)
    @JoinColumn(name = "home_club_id", nullable = false)
    private ClubEntity homeClub;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "away_club_id", nullable = false)
    private ClubEntity awayClub;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id",nullable = false)
    private StadiumEntity stadium;

    private Integer homeGoals;
    private Integer awayGoals;

    public MatchEntity() {
    }

    public MatchEntity(Long id, LocalDateTime dateTime, ClubEntity homeClub, ClubEntity awayClub,
                       StadiumEntity stadium, Integer homeGoals, Integer awayGoals) {
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

    public ClubEntity getHomeClub() {
        return homeClub;
    }

    public void setHomeClub(ClubEntity homeClub) {
        this.homeClub = homeClub;
    }

    public ClubEntity getAwayClub() {
        return awayClub;
    }

    public void setAwayClub(ClubEntity awayClub) {
        this.awayClub = awayClub;
    }

    public StadiumEntity getStadium() {
        return stadium;
    }

    public void setStadium(StadiumEntity stadium) {
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

