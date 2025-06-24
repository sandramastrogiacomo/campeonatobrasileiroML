package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.ClubRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.ClubResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.ClubEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.ClubRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {

    private final ClubRepository clubeRepository;

    public ClubService(ClubRepository clubeRepository) {
    this.clubeRepository = clubeRepository;
    }

    public ClubResponseDTO cadastrarClube(ClubRequestDTO clubeRequestDTO) {
        ClubEntity clubeEntity = new ClubEntity();

        clubeEntity.setNome(clubeRequestDTO.getNome());
        clubeEntity.setEstado(clubeRequestDTO.getEstado());
        clubeEntity.setAtivo(true);

        clubeEntity = clubeRepository.save(clubeEntity);
        return toRespostaDTO(clubeEntity);

    }

    public ClubResponseDTO atualizarClube(Long id, ClubRequestDTO clubeRequestDTO) {
        ClubEntity clubeEntity = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado!"));

        clubeEntity.setNome(clubeRequestDTO.getNome());
        clubeEntity.setEstado(clubeRequestDTO.getEstado());

        clubeEntity = clubeRepository.save(clubeEntity);
        return toRespostaDTO(clubeEntity);
    }

    public void inativarClube(Long id) {
        ClubEntity clubeEntity = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado!"));

        clubeEntity.setAtivo(false);
        clubeRepository.save(clubeEntity);
    }

    public ClubResponseDTO buscarPorId(Long id) {
        ClubEntity clubeEntity = clubeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clube não encontrado! "));
        return toRespostaDTO(clubeEntity);
    }

    public Page<ClubResponseDTO> listar(String nome, String estado, Boolean ativo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());

        Page<ClubEntity> clubes = clubeRepository.buscarComFiltros(nome,estado,ativo, pageable);

       return clubes.map(this::toRespostaDTO);
    }

    private ClubResponseDTO toRespostaDTO(ClubEntity clubeEntity) {
        return new ClubResponseDTO(
                clubeEntity.getId(),
                clubeEntity.getNome(),
                clubeEntity.getEstado(),
                clubeEntity.isAtivo());
    }

    public List<ClubResponseDTO> buscarPorNome(String nome) {
        List<ClubEntity> clubes = clubeRepository.findByNome(nome);
        return clubes.stream().map(this::toRespostaDTO).collect(Collectors.toList());
    }
}