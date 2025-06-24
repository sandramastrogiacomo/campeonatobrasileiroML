package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.StadiumRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.StadiumResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.EstadioEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.EstadioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EstadioService {


    private final EstadioRepository estadioRepository;

    public EstadioService(EstadioRepository estadioRepository) {
        this.estadioRepository = estadioRepository;
    }

    public StadiumResponseDTO cadastrarEstadio(StadiumRequestDTO stadiumRequestDTO) {
        EstadioEntity estadioEntity = new EstadioEntity();

        estadioEntity.setNome(stadiumRequestDTO.getNome());
        estadioEntity.setCapacidade(stadiumRequestDTO.getCapacidade());
        estadioEntity.setCidade(stadiumRequestDTO.getCidade());

        estadioEntity = estadioRepository.save(estadioEntity);
        return toRespostaDTO(estadioEntity);
    }

    public StadiumResponseDTO atualizarEstadio(Long id, StadiumRequestDTO stadiumRequestDTO) {
        EstadioEntity estadioEntity = estadioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));

        estadioEntity.setNome(stadiumRequestDTO.getNome());
        estadioEntity.setCapacidade(estadioEntity.getCapacidade());
        estadioEntity.setCidade(stadiumRequestDTO.getCidade());

        estadioEntity = estadioRepository.save(estadioEntity);
        return toRespostaDTO(estadioEntity);
    }

    public StadiumResponseDTO buscarPorId (Long id) {
        EstadioEntity estadioEntity = estadioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));
        return toRespostaDTO(estadioEntity);
    }

    public List<StadiumResponseDTO> buscarPorNome (String nome) {
        Optional<EstadioEntity> entidades = estadioRepository.findByNomeIgnoreCase(nome);
        return entidades.stream().map(this::toRespostaDTO).toList();
    }

    public Page<StadiumResponseDTO>listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        Page<EstadioEntity> estadios = estadioRepository.findAll(pageable);
        return estadios.map(this:: toRespostaDTO);
    }

    public Page<StadiumResponseDTO> listar(Pageable pageable) {
        return estadioRepository.findAll(pageable).map(this::toRespostaDTO);
    }

    public Page<StadiumResponseDTO> listarPorCidade(String cidade, Pageable pageable) {
        Page<EstadioEntity> page = estadioRepository.findByCidadeContainingIgnoreCase(cidade, pageable);
        return  page.map(this::toRespostaDTO);
    }

    private StadiumResponseDTO toRespostaDTO(EstadioEntity estadioEntity) {
        return new StadiumResponseDTO(
                estadioEntity.getId(),
                estadioEntity.getNome(),
                estadioEntity.getCidade(),
                estadioEntity.getCapacidade());
    }


}
