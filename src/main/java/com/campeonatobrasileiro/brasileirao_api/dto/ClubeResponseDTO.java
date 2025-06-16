package com.campeonatobrasileiro.brasileirao_api.dto;

public class ClubeResponseDTO {

    private Long id;
    private String nome;
    private String estado;
    private boolean ativo;

    public ClubeResponseDTO() {

    }
    public ClubeResponseDTO(Long id, String nome, String estado, boolean ativo) {
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
