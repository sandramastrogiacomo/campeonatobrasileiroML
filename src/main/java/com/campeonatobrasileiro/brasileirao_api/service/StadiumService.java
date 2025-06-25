package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.StadiumRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.StadiumResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.StadiumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StadiumService {


    private final StadiumRepository stadiumRepository;

    public StadiumService(StadiumRepository stadiumRepository) {
        this.stadiumRepository = stadiumRepository;
    }

    public StadiumResponseDTO registerStadium(StadiumRequestDTO stadiumRequestDTO) {
        StadiumEntity stadiumEntity = new StadiumEntity();

        stadiumEntity.setNome(stadiumRequestDTO.getNome());
        stadiumEntity.setCapacidade(stadiumRequestDTO.getCapacidade());
        stadiumEntity.setCidade(stadiumRequestDTO.getCidade());

        stadiumEntity = stadiumRepository.save(stadiumEntity);
        return toRespostaDTO(stadiumEntity);
    }

    public StadiumResponseDTO atualizarEstadio(Long id, StadiumRequestDTO stadiumRequestDTO) {
        StadiumEntity stadiumEntity = stadiumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));

        stadiumEntity.setNome(stadiumRequestDTO.getNome());
        stadiumEntity.setCapacidade(stadiumEntity.getCapacidade());
        stadiumEntity.setCidade(stadiumRequestDTO.getCidade());

        stadiumEntity = stadiumRepository.save(stadiumEntity);
        return toRespostaDTO(stadiumEntity);
    }

    public StadiumResponseDTO buscarPorId (Long id) {
        StadiumEntity stadiumEntity = stadiumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));
        return toRespostaDTO(stadiumEntity);
    }

    public List<StadiumResponseDTO> buscarPorNome (String nome) {
        Optional<StadiumEntity> entidades = stadiumRepository.findByNomeIgnoreCase(nome);
        return entidades.stream().map(this::toRespostaDTO).toList();
    }

    public Page<StadiumResponseDTO>listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        Page<StadiumEntity> estadios = stadiumRepository.findAll(pageable);
        return estadios.map(this:: toRespostaDTO);
    }

    public Page<StadiumResponseDTO> listar(Pageable pageable) {
        return stadiumRepository.findAll(pageable).map(this::toRespostaDTO);
    }

    public Page<StadiumResponseDTO> listarPorCidade(String cidade, Pageable pageable) {
        Page<StadiumEntity> page = stadiumRepository.findByCidadeContainingIgnoreCase(cidade, pageable);
        return  page.map(this::toRespostaDTO);
    }

    private StadiumResponseDTO toRespostaDTO(StadiumEntity stadiumEntity) {
        return new StadiumResponseDTO(
                stadiumEntity.getId(),
                stadiumEntity.getNome(),
                stadiumEntity.getCidade(),
                stadiumEntity.getCapacidade());
    }


}
