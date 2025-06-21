package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubeRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubeResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubeEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubeService {

    private final ClubeRepository clubeRepository;

    public ClubeService(ClubeRepository clubeRepository) {
    this.clubeRepository = clubeRepository;
    }

    public ClubeResponseDTO cadastrarClube(ClubeRequestDTO clubeRequestDTO) {
        ClubeEntity clubeEntity = new ClubeEntity();

        clubeEntity.setNome(clubeRequestDTO.getNome());
        clubeEntity.setEstado(clubeRequestDTO.getEstado());
        clubeEntity.setAtivo(true);

        clubeEntity = clubeRepository.save(clubeEntity);
        return toRespostaDTO(clubeEntity);

    }

    public ClubeResponseDTO atualizarClube(Long id, ClubeRequestDTO clubeRequestDTO) {
        ClubeEntity clubeEntity = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado!"));

        clubeEntity.setNome(clubeRequestDTO.getNome());
        clubeEntity.setEstado(clubeRequestDTO.getEstado());

        clubeEntity = clubeRepository.save(clubeEntity);
        return toRespostaDTO(clubeEntity);
    }

    public void inativarClube(Long id) {
        ClubeEntity clubeEntity = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado!"));

        clubeEntity.setAtivo(false);
        clubeRepository.save(clubeEntity);
    }

    public ClubeResponseDTO buscarPorId(Long id) {
        ClubeEntity clubeEntity = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado! "));
        return toRespostaDTO(clubeEntity);
    }

    public Page<ClubeResponseDTO> listar(String nome, String estado, Boolean ativo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());

        Page<ClubeEntity> clubes = clubeRepository.buscarComFiltros(nome,estado,ativo, pageable);

       return clubes.map(this::toRespostaDTO);
    }

    private ClubeResponseDTO toRespostaDTO(ClubeEntity clubeEntity) {
        return new ClubeResponseDTO(
                clubeEntity.getId(),
                clubeEntity.getNome(),
                clubeEntity.getEstado(),
                clubeEntity.isAtivo());
    }

    public List<ClubeResponseDTO> buscarPorNome(String nome) {
        List<ClubeEntity> clubes = clubeRepository.buscarPorNome(nome);
        return clubes.stream().map(this::toRespostaDTO).collect(Collectors.toList());
    }
}