package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.StadiumRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.StadiumResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.StadiumEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.StadiumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EstadioServiceTest {

    @InjectMocks
    private StadiumService stadiumService;

    @Mock
    private StadiumRepository stadiumRepository;
    private StadiumEntity estadio;

    @Test
    void cadastrarEstadioComSucesso() {
        StadiumRequestDTO stadiumRequestDTO = new StadiumRequestDTO();
        StadiumEntity stadiumEntity = new StadiumEntity(1l,"Allianz Park","São Paulo",50000);

        Mockito.when(stadiumRepository.save(Mockito.any(StadiumEntity.class))).thenReturn(stadiumEntity);

        StadiumResponseDTO stadiumResponseDTO = stadiumService.cadastrarEstadio(stadiumRequestDTO);

        Assertions.assertNotNull(stadiumResponseDTO);

        Assertions.assertEquals(stadiumResponseDTO.getNome(), stadiumEntity.getNome());
        Assertions.assertEquals(stadiumResponseDTO.getCidade(), stadiumEntity.getCidade());
        Assertions.assertEquals(stadiumResponseDTO.getCapacidade(), stadiumEntity.getCapacidade());

        Mockito.verify(stadiumRepository, Mockito.times(1)).save(Mockito.any(StadiumEntity.class));

    }

    @Test
    void atualizarEstadioComSucesso() {
        Long id = 1L;

        StadiumRequestDTO stadiumRequestDTO = new StadiumRequestDTO();
        stadiumRequestDTO.setNome("Neo Quimica Arena");
        stadiumRequestDTO.setCidade("Sao Paulo");
        stadiumRequestDTO.setCapacidade(47000);

        StadiumEntity stadiumEntityExistente = new StadiumEntity(id, "Allianz Park", "São Paulo", 50000);

        StadiumEntity stadiumEntityAtualizado = new StadiumEntity(id,
        stadiumRequestDTO.getNome(),
        stadiumRequestDTO.getCidade(),
        stadiumRequestDTO.getCapacidade());

        Mockito.when(stadiumRepository.findById(id)).thenReturn(Optional.of(stadiumEntityExistente));
        Mockito.when(stadiumRepository.save(Mockito.any(StadiumEntity.class))).thenReturn(stadiumEntityAtualizado);

        StadiumResponseDTO stadiumResponseDTO = stadiumService.atualizarEstadio(id, stadiumRequestDTO);

        Assertions.assertNotNull(stadiumResponseDTO);
        Assertions.assertEquals(stadiumRequestDTO.getNome(), stadiumResponseDTO.getNome());
        Assertions.assertEquals(stadiumRequestDTO.getCidade(), stadiumResponseDTO.getCidade());
        Assertions.assertEquals(stadiumRequestDTO.getCapacidade(), stadiumResponseDTO.getCapacidade());

        Mockito.verify(stadiumRepository).findById(id);
        Mockito.verify(stadiumRepository).save(Mockito.any(StadiumEntity.class));
    }
   @Test
    void buscarEstadioPorIdComSucesso() {
        Long id = 1L;
        StadiumEntity stadiumEntity = new StadiumEntity(id, "Allianz Park", "São Paulo", 50000);
        Mockito.when(stadiumRepository.findById(id)).thenReturn(Optional.of(stadiumEntity));

        StadiumResponseDTO resposta = stadiumService.buscarPorId(id);

        Assertions.assertEquals(resposta.getNome(), stadiumEntity.getNome());
        Assertions.assertEquals(resposta.getCapacidade(), stadiumEntity.getCapacidade());
        Assertions.assertEquals(resposta.getCapacidade(), stadiumEntity.getCapacidade());

        Mockito.verify(stadiumRepository, Mockito.times(1)).findById(id);

   }
    @Test
    void buscarEstadioPorNomeComSucesso() {
        String nome = "Allianz Park";
       StadiumEntity estadio = new StadiumEntity(1l,"Allianz Park","São Paulo",50000);

        Mockito.when(stadiumRepository.findByNomeIgnoreCase(nome)).thenReturn(Optional.of(estadio));

        List<StadiumResponseDTO> resultado = stadiumService. buscarPorNome(nome);

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Allianz Park", resultado.get(0).getNome());
        Assertions.assertEquals("São Paulo", resultado.get(0).getCidade());
        Assertions.assertEquals(50000, resultado.get(0).getCapacidade());

        Mockito.verify(stadiumRepository).findByNomeIgnoreCase(nome);
    }

   @Test
    void listarEstadioComPaginacao() {
       Pageable pageable = PageRequest.of(0, 10);
       List<StadiumEntity> lista = List.of( new StadiumEntity(1L, "Allianz Park", "São Paulo", 50000 ),
               new StadiumEntity(2L, "Maracanã", "Rio de Janeiro", 55000));

       Mockito.when(stadiumRepository.findAll(pageable)).thenReturn(new PageImpl<>(lista));

       Page<StadiumResponseDTO> pagina = stadiumService.listar(pageable);

       Assertions.assertEquals(2, pagina.getContent ().size());
       Assertions.assertEquals ("Allianz Park", pagina.getContent().get(0).getNome());
       Assertions.assertEquals ("Maracanã", pagina.getContent().get(1).getNome());

       Mockito.verify(stadiumRepository, Mockito.times(1)).findAll(pageable);
   }


   @Test
    void buscarEstadioPorIdDeveLancarExcecao() {
       Mockito.when(stadiumRepository.findById(999L)).thenReturn(Optional.empty());

       EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
           stadiumService.buscarPorId(999L);
       });

       assertEquals("Estádio não encontrado!", exception.getMessage());
   }

    @Test
    void listarEstadioPorCidade() {
        Pageable pageable = PageRequest.of(0, 10);
        String cidade = "São Paulo";

        StadiumEntity stadiumEntity1 = new StadiumEntity(1L, "Allianz Park", "São Paulo", 50000);
        StadiumEntity stadiumEntity2 = new StadiumEntity(2L, "Morumbis", "São Paulo", 55000);
        List<StadiumEntity> lista = List.of(stadiumEntity1, stadiumEntity2);

        Page<StadiumEntity> page = new PageImpl<>(lista);

        Mockito.when(stadiumRepository.findByCidadeContainingIgnoreCase(cidade, pageable)).thenReturn(page);

        Page<StadiumResponseDTO> resultado = stadiumService.listarPorCidade(cidade,pageable);

        Assertions.assertEquals(2, resultado.getContent().size());
        Assertions.assertEquals("Allianz Park", resultado.getContent().get(0).getNome());
        Assertions.assertEquals("Morumbis", resultado.getContent().get(1).getNome());

        Mockito.verify(stadiumRepository).findByCidadeContainingIgnoreCase(cidade, pageable);
    }

}

