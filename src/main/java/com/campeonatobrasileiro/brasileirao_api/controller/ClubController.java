package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubStatsResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.PageResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.ClubService;
import com.campeonatobrasileiro.brasileirao_api.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/clubs")
public class ClubController {


    private final ClubService clubService;
    private final MatchService matchService;

    public ClubController(ClubService clubService, MatchService matchService) {
        this.clubService = clubService;
        this.matchService = matchService;

    }

    @PostMapping
    public ClubResponseDTO createClub(@Valid @RequestBody ClubRequestDTO clubRequestDTO) {
        return clubService.createClub(clubRequestDTO);
    }

    @PutMapping("/{id}")
    public ClubResponseDTO updateClub(@Valid @RequestBody ClubRequestDTO clubRequestDTO, @PathVariable Long id) {
        return clubService.updateClub(id, clubRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deactivateClub(@PathVariable Long id) {
        clubService.deactivateClub(id);
    }

    @GetMapping("/{id}")
    public ClubResponseDTO findById(@PathVariable Long id) {
        return clubService.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<ClubResponseDTO> findByNameContainingIgnoreCase(@PathVariable String name) {
        return clubService.findByNameContainigIgnoreCase(name);

    }

    @GetMapping
    public PageResponseDTO<ClubResponseDTO> listClubs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {

        Page<ClubResponseDTO> page = clubService.list(name, state, active, pageable);
        PageResponseDTO<ClubResponseDTO> pageResponseDTO = new PageResponseDTO<>(page);
        return ResponseEntity.ok(pageResponseDTO).getBody();
    }

    @GetMapping("/clubs/{id}/stats")
    public ResponseEntity<ClubStatsResponseDTO> getClubsStats(@PathVariable Long id) {

        ClubStatsResponseDTO clubStatsResponseDTO = matchService.getClubStats(id);
        return ResponseEntity.ok(clubStatsResponseDTO);
    }
}
