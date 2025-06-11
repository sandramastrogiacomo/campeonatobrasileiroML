package com.campeonatobrasileiro.brasileirao_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class EstadioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do estádio é obrigatório!")
    @Size (min = 3, max = 100, message = "O nome deve ter no mínimo 3 e no máximo 100 caracteres!")
    private String nome;

    @NotBlank (message = "A cidade é obrigatória!")
    private String cidade;

    @Positive (message = "A capacidade deve ser positiva!")
    private Integer capacidade;

    public EstadioModel() {
    }

    public EstadioModel(Long id, String nome, String cidade, Integer capacidade) {
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
