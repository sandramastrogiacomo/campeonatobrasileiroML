package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.StadiumRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stadiums")
public class StadiumController {


    private final StadiumService stadiumService;

    public StadiumController(StadiumoService stadiumService) {
        this.StadiumService = stadiumService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StadiumResponseDTO registerStadium (@Valid @RequestBody StadiumRequestDTO stadiumRequestDTO) {
        return stadiumService.registerStadium(stadiumRequestDTO);
    }

    @PutMapping ("/{id}")
    public StadiumResponseDTO updateStadium(@Valid @RequestBody StadiumRequestDTO stadiumRequestDTO, @PathVariable Long id) {
        return StadiumService.updateStadium(id, stadiumRequestDTO);
    }

    @GetMapping ("/{id}")
    public StadiumResponseDTO findById (@PathVariable Long id) {
        return stadiumService.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<StadiumResponseDTO> findByName (@PathVariable String name) {
        return stadiumService.findByName(name);
    }

    @GetMapping("/findy-for-city")
    public Page<StadiumResponseDTO> findByCity(
        @RequestParam String city,
        @RequestParam int page,
        @RequestParam int size) {

        return stadiumService.listCity(city, PageRequest.of(page, size));
        }

    @GetMapping
    public Page<StadiumResponseDTO> listStadiums(@RequestParam(defaultValue = "0" )int page,
                                                   @RequestParam (defaultValue = "10") int size, Pageable pageable) {
        return stadiumService.list(pageable);
    }
  }

