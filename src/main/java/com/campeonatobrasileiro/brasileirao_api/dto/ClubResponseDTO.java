package com.campeonatobrasileiro.brasileirao_api.dto;

import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubResponseDTO {

    private Long id;
    private String name;
    private String state;
    private Boolean active;

    private String stadiumName;


}
