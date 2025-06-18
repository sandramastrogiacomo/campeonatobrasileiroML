package com.campeonatobrasileiro.brasileirao_api.dto;

import com.campeonatobrasileiro.brasileirao_api.entity.ClubeEntity;
import com.campeonatobrasileiro.brasileirao_api.entity.EstadioEntity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class PartidaRequestDTO {

    @NotNull(message = "A data e a hora da partida são obrigatórias!")
    @Future(message = "A data da partida deve ser no futuro!")
    private LocalDateTime dataHora;


    @NotNull(message = "O clube mandante é obrigatório!")
    private ClubeEntity clubeMandante;


    @NotNull(message = "O clube visitante é obrigatório!")
    private ClubeEntity clubeVisitante;


    @NotNull(message = "o estádio é obrigatório!")
    private EstadioEntity estadio;

    private Integer golsMandante;
    private Integer golsVisitante;

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public ClubeEntity getClubeMandante() {
        return clubeMandante;
    }

    public void setClubeMandante(ClubeEntity clubeMandante) {
        this.clubeMandante = clubeMandante;
    }

    public ClubeEntity getClubeVisitante() {
        return clubeVisitante;
    }

    public void setClubeVisitante(ClubeEntity clubeVisitante) {
        this.clubeVisitante = clubeVisitante;
    }

    public EstadioEntity getEstadio() {
        return estadio;
    }

    public void setEstadio(EstadioEntity estadio) {
        this.estadio = estadio;
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
