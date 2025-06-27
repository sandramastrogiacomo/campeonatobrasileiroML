package com.campeonatobrasileiro.brasileirao_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubRequestDTO {

    @NotBlank(message = "O nome do clube é obrigatório!")
    @Size(min = 3, max = 100, message = "O nome do clube deve ter no mínimo 3 e no máximo 100 caracteres!")
    private String name;

    @NotBlank(message = "O estado do clube é obrigatório!")
    @Size(min = 2, max = 10, message = "O nome do estado deve ter no mínimo 2 e no máximo 100 carateres!")
    private String state;

    @NotNull(message = "O campo 'active' é obrigatório!")
    private Boolean active;

    @NotNull
    private Long stadiumId;


}

