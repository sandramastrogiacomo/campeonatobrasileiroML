package com.campeonatobrasileiro.brasileirao_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "clubes")
public class ClubeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "O nome do clube é obrigatório!")
    @Size(min = 3, max = 100, message = "O nome do clube deve ter no minímo 3 e no máximo 100 caracteres!")
    private String nome;

    @NotBlank (message = "O estado do clube é obrigatório!")
    @Size(min = 2, max = 10, message = "O nome do estado deve ter no mímino 2 e no máximo 10 caracteres!")
    private String estado;

    private boolean ativo = true;

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

    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo( boolean ativo) {
        this.ativo = ativo;
    }
}
