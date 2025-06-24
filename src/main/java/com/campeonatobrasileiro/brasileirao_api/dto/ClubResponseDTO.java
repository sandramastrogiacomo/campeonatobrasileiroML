package com.campeonatobrasileiro.brasileirao_api.dto;

public class ClubResponseDTO {

    private Long id;
    private String name;
    private String state;
    private boolean active;

    public ClubResponseDTO() {

    }
    public ClubResponseDTO(Long id, String name, String state, Boolean active) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
