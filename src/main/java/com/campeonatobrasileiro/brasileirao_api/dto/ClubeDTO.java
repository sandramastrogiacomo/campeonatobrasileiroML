package com.campeonatobrasileiro.brasileirao_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClubeDTO {

    @NotBlank(message = "O nome do clube é obrigatório!")
    @Size(min = 3, max = 100, message = "O nome do clube deve ter no mínimo 3 e no máximo 100 caracteres!")
    private String nome;

    @NotBlank(message = "O estado do clube é obrigatório!")
    @Size(min = 2, max = 10, message = "O nome do estado deve ter no mínimo 2 e no máximo 100 carateres!")
    private String estado;

    public ClubeDTO() {

    }

    public ClubeDTO(String nome, String estado) {
        this.nome = nome;
        this.estado = estado;
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
}

