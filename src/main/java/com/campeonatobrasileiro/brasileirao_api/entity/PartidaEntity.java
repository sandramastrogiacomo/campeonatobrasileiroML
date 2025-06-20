package com.campeonatobrasileiro.brasileirao_api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "partidas")
public class PartidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @ManyToOne (fetch =  FetchType.LAZY)
    @JoinColumn(name = "clube_mandante_id", nullable = false)
    private ClubeEntity clubeMandante;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "clube_visitante_id", nullable = false)
    private ClubeEntity clubeVisitante;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "estadio_id",nullable = false)
    private EstadioEntity estadio;

    private Integer golsMandante;
    private Integer golsVisitante;

    public PartidaEntity() {
    }

    public PartidaEntity(Long id, LocalDateTime dataHora, ClubeEntity clubeMandante, ClubeEntity clubeVisitante, EstadioEntity estadio, Integer golsMandante, Integer golsVisitante) {
        this.id = id;
        this.dataHora = dataHora;
        this.clubeMandante = clubeMandante;
        this.clubeVisitante = clubeVisitante;
        this.estadio = estadio;
        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;
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

