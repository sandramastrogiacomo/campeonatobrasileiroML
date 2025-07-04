package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.*;
import com.campeonatobrasileiro.brasileirao_api.enums.RankingCriteria;
import com.campeonatobrasileiro.brasileirao_api.service.ClubService;
import com.campeonatobrasileiro.brasileirao_api.service.MatchService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs")
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
        return clubService.findByNameContainingIgnoreCase(name);
    }

    @GetMapping
    public PageResponseDTO<ClubResponseDTO> listClubs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {
        Page<ClubResponseDTO> page = clubService.list(name, state, active, pageable);
        return new PageResponseDTO<>(page);
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<ClubStatsResponseDTO> getClubsStats(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.getClubStats(id));
    }

    @GetMapping("/{id}/stats/opponents")
    public ResponseEntity<List<ClubStatsResponseDTO>> getStatsAgainstOpponents(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.getStatsAgainstOpponents(id));
    }

    @GetMapping("/{clubId}/stats/opponent/{opponentId}")
    public ResponseEntity<List<ClubHeadToHeadDTO>>getHeadToHeadStats(@PathVariable Long clubId,
                                                                         @PathVariable Long opponentId) {
        return ResponseEntity.ok(matchService.getHeadToHeadStats(clubId, opponentId));
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<ClubRankingResponseDTO>> getClubRanking(
            @RequestParam(required = false) RankingCriteria sortBy) {
        if (sortBy != null) {
            return ResponseEntity.ok(clubService.getClubRankingSortedBy(sortBy));
        }

        return ResponseEntity.ok(clubService.getClubRanking());
    }
}