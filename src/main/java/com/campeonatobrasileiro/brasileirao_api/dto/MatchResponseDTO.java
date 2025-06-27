package com.campeonatobrasileiro.brasileirao_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponseDTO {

    private Long id;
    private LocalDateTime dateTime;
    private String homeClub;
    private String awayClub;
    private String stadium;
    private Integer homeGoals;
    private Integer awayGoals;

}
