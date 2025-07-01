package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.MatchRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.MatchResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public MatchResponseDTO registerMatch(@RequestBody @Valid MatchRequestDTO matchRequestDTO) {
        return matchService.registerMatch(matchRequestDTO);
    }

    @GetMapping("/{id}")
    public MatchResponseDTO findById(@PathVariable Long id) {
        return matchService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
    }

    @GetMapping
    public Page<MatchResponseDTO> listMatches(Pageable pageable) {
    return matchService.listMatches(pageable);
    }

    @GetMapping("/homeClub/{id}")
    public Page<MatchResponseDTO> listMatchesByHomeClub(@PathVariable Long id, Pageable pageable) {
    return matchService.listMatchesByHomeClub(id, pageable);
    }

    @GetMapping("/awayClub/{id}")
    public Page<MatchResponseDTO> listMatchesByAwayClub(@PathVariable Long id, Pageable pageable) {
        return matchService.listMatchesByAwayClub(id, pageable);
    }

    @GetMapping("/stadium/{id}")
    public Page<MatchResponseDTO> listMatchesByStadium(@PathVariable Long id, Pageable pageable) {
        return matchService.listMatchesByStadium (id,pageable);
    }
}
