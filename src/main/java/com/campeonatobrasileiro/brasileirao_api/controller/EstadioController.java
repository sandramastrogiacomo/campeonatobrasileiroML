package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.EstadioRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.EstadioResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.EstadioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estadios")
public class EstadioController {


    private final EstadioService estadioService;

    public EstadioController(EstadioService estadioService) {
        this.estadioService = estadioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadioResponseDTO cadastrarEstadio (@Valid @RequestBody EstadioRequestDTO estadioRequestDTO) {
        return estadioService.cadastrarEstadio(estadioRequestDTO);
    }

    @PutMapping ("/{id}")
    public EstadioResponseDTO atualizarEstadio (@Valid @RequestBody EstadioRequestDTO estadioRequestDTO, @PathVariable Long id) {
        return estadioService.atualizarEstadio(id, estadioRequestDTO);
    }

    @GetMapping ("/{id}")
    public EstadioResponseDTO buscarPorId (@PathVariable Long id) {
        return estadioService.buscarPorId(id);
    }

    @GetMapping("/nome/{nome}")
    public List<EstadioResponseDTO> buscarPorNome (@PathVariable String nome) {
        return estadioService.buscarPorNome(nome);
    }

    @GetMapping("/buscar-por-cidade")
    public Page<EstadioResponseDTO> buscarPorCidade (
        @RequestParam String cidade,
        @RequestParam int page,
        @RequestParam int size) {

        return estadioService.listarPorCidade(cidade, PageRequest.of(page, size));
        }

    @GetMapping
    public Page<EstadioResponseDTO> listarEstadios(@RequestParam(defaultValue = "0" )int page,
                                                   @RequestParam (defaultValue = "10") int size, Pageable pageable) {
        return estadioService.listar(pageable);
    }
  }

