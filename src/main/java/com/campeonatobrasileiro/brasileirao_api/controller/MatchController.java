package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubHeadToHeadDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.MatchRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.MatchResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.PageResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
        return matchService.listMatchesByStadium(id, pageable);
    }

    @GetMapping("/search-by-date")
    public ResponseEntity<PageResponseDTO<MatchResponseDTO>> searchNatchesByDate(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable) {

        Page<MatchResponseDTO> page = matchService.findMatchesByDateRanger(startDate, endDate, pageable);
        PageResponseDTO<MatchResponseDTO> responseDTO = new PageResponseDTO<>(page);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/search-by-stadium-city")
    public ResponseEntity<PageResponseDTO<MatchResponseDTO>> searchMatchesByStadiumCity(
            @RequestParam String city, Pageable pageable) {

        Page<MatchResponseDTO> page = matchService.listMatchesByStadiumCity(city, pageable);
        return ResponseEntity.ok(new PageResponseDTO<>(page));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDTO<MatchResponseDTO>> searchMatches(
            @RequestParam(required = false) Long clubId,
            @RequestParam(required = false) Long stadiumId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, Pageable pageable) {

        Page<MatchResponseDTO> page = matchService.searchMatchesWithFilters(clubId, stadiumId, startDate, endDate, pageable);

        return ResponseEntity.ok(new PageResponseDTO<>(page));

    }

    @GetMapping("/{club1Id}/direct-head-to-head/{club2Id}")
    public ResponseEntity<List<ClubHeadToHeadDTO>>getDirectHeadToHead(
            @PathVariable Long club1Id, @PathVariable Long club2Id) {
        List<ClubHeadToHeadDTO> stats = matchService.getDirectHeadToHeadStats(club1Id,club2Id);
        return ResponseEntity.ok(stats);
    }
}