package com.campeonatobrasileiro.brasileirao_api.dto;

import jakarta.validation.constraints.*;

public class StadiumRequestDTO {

    @NotBlank(message = "O nome do estádio é obrigatório!")
    @Size(min = 3, max = 100, message = "O nome deve ter no mínimo 3 e no máximo 100 caracteres!")
    private String name;

    @NotBlank(message = "A cidade é obrigatória!")
    private String city;

    @NotNull(message = "A cpacidade é obrigatória!")
    @Positive(message = "A capacidade deve ser positiva!")
    private Integer capacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
