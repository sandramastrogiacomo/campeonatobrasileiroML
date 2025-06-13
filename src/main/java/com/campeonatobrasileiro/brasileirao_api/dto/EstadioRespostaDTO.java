package com.campeonatobrasileiro.brasileirao_api.dto;

public class EstadioRespostaDTO {
    private Long id;
    private String nome;
    private String cidade;
    private Integer capacidade;

    public EstadioRespostaDTO(Long id, String nome, String cidade, Integer capacidade) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }
}
