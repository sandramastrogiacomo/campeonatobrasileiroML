package com.campeonatobrasileiro.brasileirao_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class PartidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data e a hora da partida são obrigatórias!")
    @Future(message = "A data da partida deve ser no futuro!")
    private LocalDateTime dataHora;

    @ManyToOne (fetch =  FetchType.LAZY)
    @JoinColumn(name = "clube_mandante_id")
    @NotNull(message = "O clube mandante é obrigatório!")
    private ClubeEntity clubeMandante;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "clube_visitante_id")
    @NotNull(message = "O clube visitante é obrigatório!")
    private ClubeEntity clubeVisitante;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "estadop_id")
    @NotNull(message = "o estádio é obrigatório!")
    private EstadioEntity estadio;

    private Integer golsMandante;
    private Integer golsVisitante;

    public PartidaEntity() {
    }

    public PartidaEntity(Long id, LocalDateTime dataHora, ClubeEntity clubeMandante, ClubeEntity clubeVisitante, EstadioEntity estadio, Integer gomMandante, Integer gomVisitante) {
        this.id = id;
        this.dataHora = dataHora;
        this.clubeMandante = clubeMandante;
        this.clubeVisitante = clubeVisitante;
        this.estadio = estadio;
        this.golsMandante = gomMandante;
        this.golsVisitante = gomVisitante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

