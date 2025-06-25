package com.campeonatobrasileiro.brasileirao_api.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "clubes")
public class ClubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String state;

    private boolean active = true;

    @OneToMany (mappedBy = "homeClub", fetch = FetchType.LAZY)
    private List<MatchEntity> homeMatches;

    @OneToMany(mappedBy = "awayClub", fetch = FetchType.LAZY)
    private List<MatchEntity>awayMatches;


    public ClubEntity() {
    }

    public ClubEntity(Long id, String name, String state, boolean active) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<MatchEntity> getHomeMatches() {
        return homeMatches;
    }

    public void setHomeMatches(List<MatchEntity> homeMatches) {
        this.homeMatches = homeMatches;
    }

    public List<MatchEntity> getAwayMatches() {
        return awayMatches;
    }

    public void setAwayMatches(List<MatchEntity> awayMatches) {
        this.awayMatches = awayMatches;
    }
}
