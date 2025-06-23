package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.PartidaRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.PartidaResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping
    public PartidaResponseDTO cadastrarPartida(@RequestBody @Valid PartidaRequestDTO partidaRequestDTO) {
        return partidaService.cadastrarPartida(partidaRequestDTO);
    }

    @GetMapping("/{id}")
    public PartidaResponseDTO buscarPartidaPorId(@PathVariable Long id) {
        return partidaService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPartidaPorId(@PathVariable Long id) {
        partidaService.deletarPartida(id);
    }

    @GetMapping
    public Page<PartidaResponseDTO> listarPartidas(Pageable pageable) {
    return partidaService.listarPartidas(pageable);
    }

    @GetMapping("/clubeMandante/{id}")
    public Page<PartidaResponseDTO> listarPartidasPorClubeMandante(@PathVariable Long id, Pageable pageable) {
    return partidaService.listarPorClubeMandante(id, pageable);
    }

    @GetMapping("/clubeVisitante/{id}")
    public Page<PartidaResponseDTO> listarPartidasPorClubeVisitante(@PathVariable Long id, Pageable pageable) {
        return partidaService.listarPorClubeVisitante(id, pageable);
    }

    @GetMapping("/estadio/{id}")
    public Page<PartidaResponseDTO> listarPartidasPorEstadio(@PathVariable Long id, Pageable pageable) {
        return partidaService.listarPorEstadio (id,pageable);
    }
}
