package com.campeonatobrasileiro.brasileirao_api.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "clubes")
public class ClubeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String estado;

    private boolean ativo = true;

    @OneToMany (mappedBy = "ClubeMandante", fetch = FetchType.LAZY)
    private List<PartidaEntity> partidasComoMandante;

    @OneToMany(mappedBy = "ClubeVisitante", fetch = FetchType.LAZY)
    private List<PartidaEntity> partidasComoVisitante;


    public ClubeEntity() {
    }

    public ClubeEntity(Long id, String nome, String estado, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public  String getEstado() {
        return estado;
    }

    public void setEstado( String estado) {
        this.estado = estado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo( boolean ativo) {
        this.ativo = ativo;
    }
}
