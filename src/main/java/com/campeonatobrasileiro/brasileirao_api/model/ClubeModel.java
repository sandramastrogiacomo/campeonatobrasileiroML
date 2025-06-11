package com.campeonatobrasileiro.brasileirao_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class ClubeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "O nome do clube é obrigatório!")
    @Size(min = 3, max = 100, message = "O nome do clube deve ter no minímo 3 e no máximo 100 caracteres!")
    private String nome;

    @NotBlank (message = "O estado do clube é obrigatório!")
    @Size(min = 2, max = 2, message = "O estado deve ter 2 letras!")
    private String estado;

    public ClubeModel() {
    }

    public ClubeModel(Long id, String nome, String estado) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
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
}
