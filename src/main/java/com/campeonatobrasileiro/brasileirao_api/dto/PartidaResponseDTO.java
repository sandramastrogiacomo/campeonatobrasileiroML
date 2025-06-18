package com.campeonatobrasileiro.brasileirao_api.dto;

import java.time.LocalDateTime;

public class PartidaResponseDTO {

private Long id;
private LocalDateTime dataHora;
private String clubeMandante;
private String clubeVisitante;
private String estadio;
private Integer golsMandante;
private Integer golsVisitante;

    public PartidaResponseDTO(Long id, LocalDateTime dataHora, String clubeMandante, String clubeVisitante, String estadio, Integer golsMandante, Integer golsVisitante) {
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

    public String getClubeMandante() {
        return clubeMandante;
    }

    public void setClubeMandante(String clubeMandante) {
        this.clubeMandante = clubeMandante;
    }

    public String getClubeVisitante() {
        return clubeVisitante;
    }

    public void setClubeVisitante(String clubeVisitante) {
        this.clubeVisitante = clubeVisitante;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
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
