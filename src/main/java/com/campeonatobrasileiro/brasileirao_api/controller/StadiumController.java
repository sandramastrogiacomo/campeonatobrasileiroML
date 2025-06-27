package com.campeonatobrasileiro.brasileirao_api.controller;

import com.campeonatobrasileiro.brasileirao_api.dto.StadiumRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.StadiumResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.service.StadiumService;
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

    public StadiumController(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
    }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public StadiumResponseDTO registerStadium (@Valid @RequestBody StadiumRequestDTO stadiumRequestDTO) {
            return stadiumService.registerStadium(stadiumRequestDTO);
        }

        @PutMapping("/{id}")
        public StadiumResponseDTO updateStadium (@Valid @RequestBody StadiumRequestDTO stadiumRequestDTO,
                @PathVariable Long id){
            return stadiumService.updateStadium (id, stadiumRequestDTO);
        }

        @GetMapping("/{id}")
        public StadiumResponseDTO findById (@PathVariable Long id){
            return stadiumService.findById(id);
        }

        @GetMapping("/name/{name}")
        public List<StadiumResponseDTO> findByName (@PathVariable String name){
            return stadiumService.findByName(name);
        }

        @GetMapping("/find-for-city")
        public Page<StadiumResponseDTO> findByCity (
                @RequestParam String city,
                @RequestParam int page,
                @RequestParam int size) {

            return stadiumService.listByCity(city, PageRequest.of(page, size));
        }

        @GetMapping
        public Page<StadiumResponseDTO> listStadiums ( @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size, Pageable pageable){
            return stadiumService.list(pageable);
        }
    }


