package com.campeonatobrasileiro.brasileirao_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "partidas")
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private LocalDate matchDate;

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

}

