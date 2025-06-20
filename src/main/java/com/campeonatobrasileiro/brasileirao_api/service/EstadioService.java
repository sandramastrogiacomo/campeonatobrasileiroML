package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.EstadioRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.EstadioResponseDTO;
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
import java.util.stream.Collectors;

@Service
public class EstadioService {


    private final EstadioRepository estadioRepository;

    public EstadioService(EstadioRepository estadioRepository) {
        this.estadioRepository = estadioRepository;
    }

    public EstadioResponseDTO cadastrarEstadio(EstadioRequestDTO estadioRequestDTO) {
        EstadioEntity estadioEntity = new EstadioEntity();

        estadioEntity.setNome(estadioRequestDTO.getNome());
        estadioEntity.setCapacidade(estadioRequestDTO.getCapacidade());
        estadioEntity.setCidade(estadioRequestDTO.getCidade());

        estadioEntity = estadioRepository.save(estadioEntity);
        return toRespostaDTO(estadioEntity);
    }

    public EstadioResponseDTO atualizarEstadio(Long id, EstadioRequestDTO estadioRequestDTO) {
        EstadioEntity estadioEntity = estadioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));

        estadioEntity.setNome(estadioRequestDTO.getNome());
        estadioEntity.setCapacidade(estadioEntity.getCapacidade());
        estadioEntity.setCidade(estadioRequestDTO.getCidade());

        estadioEntity = estadioRepository.save(estadioEntity);
        return toRespostaDTO(estadioEntity);
    }

    public EstadioResponseDTO buscarPorId (Long id) {
        EstadioEntity estadioEntity = estadioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));
        return toRespostaDTO(estadioEntity);
    }

    public List<EstadioResponseDTO> buscarPorNome (String nome) {
        Optional<EstadioEntity> entidades = estadioRepository.findByNomeIgnoreCase(nome);
        return entidades.stream().map(this::toRespostaDTO).toList();
    }

    public Page<EstadioResponseDTO>listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        Page<EstadioEntity> estadios = estadioRepository.findAll(pageable);
        return estadios.map(this:: toRespostaDTO);
    }

    public Page<EstadioResponseDTO> listar(Pageable pageable) {
        return estadioRepository.findAll(pageable).map(this::toRespostaDTO);
    }

    public Page<EstadioResponseDTO> listarPorCidade(String cidade, Pageable pageable) {
        Page<EstadioEntity> page = estadioRepository.findByCidadeContainingIgnoreCase(cidade, pageable);
        return  page.map(this::toRespostaDTO);
    }

    private EstadioResponseDTO toRespostaDTO(EstadioEntity estadioEntity) {
        return new EstadioResponseDTO(
                estadioEntity.getId(),
                estadioEntity.getNome(),
                estadioEntity.getCidade(),
                estadioEntity.getCapacidade());
    }


}
