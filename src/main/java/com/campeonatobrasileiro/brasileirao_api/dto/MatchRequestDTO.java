package com.campeonatobrasileiro.brasileirao_api.dto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


}