package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.EstadioDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.EstadioRespostaDTO;
import com.campeonatobrasileiro.brasileirao_api.model.EstadioModel;
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

    public EstadioRespostaDTO cadastrarEstadio(EstadioDTO estadioDTO) {
        EstadioModel estadioModel = new EstadioModel();

        estadioModel.setNome(estadioDTO.getNome());
        estadioModel.setCapacidade(estadioDTO.getCapacidade());
        estadioModel.setCidade(estadioDTO.getCidade());

        estadioModel = estadioRepository.save(estadioModel);
        return toRespostaDTO(estadioModel);
    }

    public EstadioRespostaDTO atualizarEstadio(Long id, EstadioDTO estadioDTO) {
        EstadioModel estadioModel =
                estadioRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException("Estádio não encontrado!"));

        estadioModel.setNome(estadioDTO.getNome());
        estadioModel.setCapacidade(estadioDTO.getCapacidade());
        estadioModel.setCidade(estadioDTO.getCidade());
        estadioModel = estadioRepository.save(estadioModel);
        return toRespostaDTO(estadioModel);
    }

    public EstadioRespostaDTO buscarPorId (Long id) {
        EstadioModel estadioModel = estadioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));
        return toRespostaDTO(estadioModel);
    }

    public EstadioRespostaDTO buscarPorNome (String nome) {
        EstadioModel estadioModel = estadioRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado!"));
        return toRespostaDTO(estadioModel);
    }

    public Page<EstadioRespostaDTO>listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        Page<EstadioModel> estadios = estadioRepository.findAll(pageable);
        return estadios.map(this:: toRespostaDTO);
    }

    private EstadioRespostaDTO toRespostaDTO(EstadioModel estadioModel) {
        return new EstadioRespostaDTO(
                estadioModel.getId(),
                estadioModel.getNome(),
                estadioModel.getCidade(),
                estadioModel.getCapacidade());
    }

}
