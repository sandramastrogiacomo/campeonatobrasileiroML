package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubeDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubeRespostaDTO;
import com.campeonatobrasileiro.brasileirao_api.model.ClubeModel;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ClubeService {

    @Autowired
    private ClubeRepository clubeRepository;

    public ClubeService(ClubeRepository clubeRepository) {
    this.clubeRepository = clubeRepository;
    }

    public ClubeRespostaDTO cadastrarClube(ClubeDTO clubeDTO) {
        ClubeModel clubeModel = new ClubeModel();

        clubeModel.setNome(clubeDTO.getNome());
        clubeModel.setEstado(clubeDTO.getEstado());
        clubeModel.setAtivo(true);

        clubeModel = clubeRepository.save(clubeModel);
        return toRespostaDTO(clubeModel);

    }

    public ClubeRespostaDTO atualizarClube(Long id, ClubeDTO clubeDTO) {
        ClubeModel clubeModel = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado!"));

        clubeModel.setNome(clubeDTO.getNome());
        clubeModel.setEstado(clubeDTO.getEstado());

        clubeModel = clubeRepository.save(clubeModel);
        return toRespostaDTO(clubeModel);
    }

    public void inativarClube(Long id) {
        ClubeModel clubeModel = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrando!"));

        clubeModel.setAtivo(false);
        clubeRepository.save(clubeModel);
    }

    public ClubeRespostaDTO buscarPorId(Long id) {
        ClubeModel clubeModel = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado! "));
        return toRespostaDTO(clubeModel);
    }

    public Page<ClubeModel> listarClubes(String nome, String estado, Pageable pageable) {
        return  clubeRepository.findByNomeContainingIgnoreCaseAndAtivo("Palmeiras", true, pageable);
    }

    public Page<ClubeRespostaDTO> listar(String nome, String estado, Boolean ativo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());

        Page<ClubeModel> clubes;
        if (estado != null && ativo != null) {
            clubes = clubeRepository.findByEstadoIgnoreCaseAndAtivo (estado, ativo, pageable);
        } else if (estado != null) {
            clubes = clubeRepository.findByEstadoIgnoreCase(estado, pageable);
        } else if (nome != null && ativo != null) {
            clubes = clubeRepository.findByNomeContainingIgnoreCaseAndAtivo(nome, ativo, pageable);
        } else if (ativo != null) {
            clubes = clubeRepository.findByAtivo(ativo, pageable);
        } else {
            clubes = clubeRepository.findAll(pageable);
        }
        return clubes.map(this::toRespostaDTO);
    }
    private ClubeRespostaDTO toRespostaDTO(ClubeModel clubeModel) {
        return new ClubeRespostaDTO(
                clubeModel.getId(),
                clubeModel.getNome(),
                clubeModel.getEstado(),
                clubeModel.isAtivo());
    }
}