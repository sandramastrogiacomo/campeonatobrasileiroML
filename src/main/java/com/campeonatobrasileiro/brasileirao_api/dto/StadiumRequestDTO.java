package com.campeonatobrasileiro.brasileirao_api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StadiumRequestDTO {

    @NotBlank(message = "O nome do estádio é obrigatório!")
    @Size(min = 3, max = 100, message = "O nome deve ter no mínimo 3 e no máximo 100 caracteres!")
    private String name;

    @NotBlank(message = "A cidade é obrigatória!")
    private String city;

    @NotNull(message = "A cpacidade é obrigatória!")
    @Positive(message = "A capacidade deve ser positiva!")
    private Integer capacity;


}
