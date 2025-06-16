package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.EstadioRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.EstadioResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.EstadioEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.EstadioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EstadioService {

    @Autowired
    private EstadioRepository estadioRepository;

    public EstadioResponseDTO cadastrarEstadio(EstadioRequestDTO estadioRequestDTO) {
        EstadioEntity estadioEntity = new EstadioEntity();

        estadioEntity.setNome(estadioRequestDTO.getNome());
        estadioEntity.setCapacidade(estadioRequestDTO.getCapacidade());
        estadioEntity.setCidade(estadioRequestDTO.getCidade());

        estadioEntity = estadioRepository.save(estadioEntity);
        return toRespostaDTO(estadioEntity);
    }

    public EstadioResponseDTO atualizarEstadio(Long id, EstadioRequestDTO estadioRequestDTO) {
        EstadioEntity estadioEntity = new EstadioEntity();
                estadioRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException("Estádio não encontrado!"));

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

    public EstadioResponseDTO buscarPorNome (String nome) {
        EstadioEntity estadioEntity = estadioRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));
        return toRespostaDTO(estadioEntity);
    }

    public Page<EstadioResponseDTO>listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        Page<EstadioEntity> estadios = estadioRepository.findAll(pageable);
        return estadios.map(this:: toRespostaDTO);
    }

    private EstadioResponseDTO toRespostaDTO(EstadioEntity estadioEntity) {
        return new EstadioResponseDTO(
                estadioEntity.getId(),
                estadioEntity.getNome(),
                estadioEntity.getCidade(),
                estadioEntity.getCapacidade());
    }

}
