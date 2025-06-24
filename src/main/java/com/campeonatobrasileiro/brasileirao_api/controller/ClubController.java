package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.ClubService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/clubs")
public class ClubController {


    private final ClubService clubService;
    public ClubController(ClubService clubService) {
        this.clubService = clubService;

    }

    @PostMapping
    public ClubResponseDTO createClub (@Valid @RequestBody ClubRequestDTO clubRequestDTO) {
        return clubService.createClub(clubRequestDTO);
    }

    @PutMapping("/{id}")
    public ClubResponseDTO updateClub(@Valid @RequestBody ClubRequestDTO clubRequestDTO, @PathVariable Long id) {
        return clubService.updateClub(id, clubRequestDTO);
    }

    @DeleteMapping ("/{id}")
    public void deactivateClub(@PathVariable Long id) {
        clubService.deactivateClub(id);
    }

    @GetMapping("/{id}")
    public ClubResponseDTO findById(@PathVariable Long id) {
        return clubService.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<ClubResponseDTO> findByName(@PathVariable String name) {
        return clubService.findByName(name);

    }

    @GetMapping
    public Page<ClubResponseDTO> list(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String state,
                                        @RequestParam(required = false) Boolean active,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        return clubService.list(name, state, active, page, size);
    }
}
