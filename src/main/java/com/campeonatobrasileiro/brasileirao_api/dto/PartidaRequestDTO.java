package com.campeonatobrasileiro.brasileirao_api.dto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class PartidaRequestDTO {

    @NotNull(message = "A data e a hora da partida são obrigatórias!")
    @Future(message = "A data da partida deve ser no futuro!")
    private LocalDateTime dataHora;


    @NotNull(message = "O clube mandante é obrigatório!")
    private Long clubeMandanteId;


    @NotNull(message = "O clube visitante é obrigatório!")
    private Long clubeVisitanteId;


    @NotNull(message = "o estádio é obrigatório!")
    private Long estadioId;

    private Integer golsMandante;
    private Integer golsVisitante;

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Long getClubeMandanteId() {
        return clubeMandanteId;
    }

    public void setClubeMandanteId(Long clubeMandanteId) {
        this.clubeMandanteId = clubeMandanteId;
    }

    public Long getClubeVisitanteId() {
        return clubeVisitanteId;
    }

    public void setClubeVisitanteId(Long clubeVisitanteId) {
        this.clubeVisitanteId = clubeVisitanteId;
    }

    public Long getEstadioId() {
        return estadioId;
    }

    public void setEstadioId(Long estadioId) {
        this.estadioId = estadioId;
    }

    public Integer getGolsMandante() {
        return golsMandante;
    }

    public void setGolsMandante(Integer golsMandante) {
        this.golsMandante = golsMandante;
    }

    public Integer getGolsVisitante() {
        return golsVisitante;
    }

    public void setGolsVisitante(Integer golsVisitante) {
        this.golsVisitante = golsVisitante;
    }
}