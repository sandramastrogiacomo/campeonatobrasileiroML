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
@Table (name = "estadios")
public class StadiumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Integer capacity;

    @OneToMany (mappedBy = "stadium", fetch = FetchType.LAZY)
    private List<MatchEntity> matches;

}


