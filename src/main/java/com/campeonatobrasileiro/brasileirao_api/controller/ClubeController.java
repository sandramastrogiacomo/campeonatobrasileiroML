package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubeRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubeResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.ClubeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/clubes")
public class ClubeController {

    @Autowired
    private ClubeService clubeService;

    @PostMapping
    public ClubeResponseDTO cadastrarClube (@Valid @RequestBody ClubeRequestDTO clubeRequestDTO) {
        return clubeService.cadastrarClube(clubeRequestDTO);
    }

    @PutMapping("/{id}")
    public ClubeResponseDTO atualizarClube(@Valid @RequestBody ClubeRequestDTO clubeRequestDTO, @PathVariable Long id) {
        return clubeService.atualizarClube(id, clubeRequestDTO);
    }

    @DeleteMapping ("/{id}")
    public void inativarClube(@PathVariable Long id) {
        clubeService.inativarClube(id);
    }

    @GetMapping("/{id}")
    public ClubeResponseDTO buscarPorId(@PathVariable Long id) {
        return clubeService.buscarPorId(id);
    }

    @GetMapping
    public Page<ClubeResponseDTO> listar(@RequestParam(required = false) String nome,
                                         @RequestParam(required = false) String estado,
                                         @RequestParam(required = false) Boolean ativo,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size){
        return clubeService.listar(nome, estado, ativo, page, size);
    }
}
