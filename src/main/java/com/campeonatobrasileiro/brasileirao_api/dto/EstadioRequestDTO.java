package com.campeonatobrasileiro.brasileirao_api.dto;

import jakarta.validation.constraints.*;

public class EstadioRequestDTO {

    @NotBlank(message = "O nome do estádio é obrigatório!")
    @Size(min = 3, max = 100, message = "O nome deve ter no mínimo 3 e no máximo 100 caracteres!")
    private String nome;

    @NotBlank (message = "A cidade é obrigatória!")
    private String cidade;

    @NotNull (message = "A cpacidade é obrigatória!")
    @Positive (message = "A capacidade deve ser positiva!")
    private Integer capacidade;

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
