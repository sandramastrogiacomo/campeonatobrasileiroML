package com.campeonatobrasileiro.brasileirao_api.controller;


import com.campeonatobrasileiro.brasileirao_api.dto.EstadioDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.EstadioRespostaDTO;
import com.campeonatobrasileiro.brasileirao_api.service.EstadioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estadios")
public class EstadioController {

    @Autowired
    private EstadioService estadioService;

    @PostMapping
    public EstadioRespostaDTO cadastrarEstadio (@Valid @RequestBody EstadioDTO estadioDTO) {
        return estadioService.cadastrarEstadio(estadioDTO);
    }

    @PutMapping ("/{id}")
    public EstadioRespostaDTO atualizarEstadio (@Valid @RequestBody EstadioDTO estadioDTO, @PathVariable Long id) {
        return estadioService.atualizarEstadio(id, estadioDTO);
    }

    @GetMapping ("/{id}")
    public EstadioRespostaDTO buscarPorId (@PathVariable Long id) {
        return estadioService.buscarPorId(id);
    }

    @GetMapping("/nome/{nome}")
    public EstadioRespostaDTO buscarPorNome (@PathVariable String nome) {
        return estadioService.buscarPorNome(nome);
    }

    @GetMapping
    public Page<EstadioRespostaDTO> listarEstadios( @RequestParam(defaultValue = "0" )int page,
                                                    @RequestParam (defaultValue = "10") int size) {
        return estadioService.listar(page, size);
    }
}

