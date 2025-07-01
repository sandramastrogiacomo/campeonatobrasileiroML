package com.campeonatobrasileiro.brasileirao_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "clubes")
public class ClubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String state;
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private StadiumEntity stadium;

    @OneToMany (mappedBy = "homeClub", fetch = FetchType.LAZY)
    private List<MatchEntity> homeMatches;

    @OneToMany(mappedBy = "awayClub", fetch = FetchType.LAZY)
    private List<MatchEntity>awayMatches;


}
