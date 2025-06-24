package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.StadiumRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.StadiumResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.EstadioEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.EstadioRepository;
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
    private EstadioService estadioService;

    @Mock
    private EstadioRepository estadioRepository;
    private EstadioEntity estadio;

    @Test
    void cadastrarEstadioComSucesso() {
        StadiumRequestDTO stadiumRequestDTO = new StadiumRequestDTO();
        EstadioEntity estadioEntity = new EstadioEntity(1l,"Allianz Park","São Paulo",50000);

        Mockito.when(estadioRepository.save(Mockito.any(EstadioEntity.class))).thenReturn(estadioEntity);

        StadiumResponseDTO stadiumResponseDTO = estadioService.cadastrarEstadio(stadiumRequestDTO);

        Assertions.assertNotNull(stadiumResponseDTO);

        Assertions.assertEquals(stadiumResponseDTO.getNome(), estadioEntity.getNome());
        Assertions.assertEquals(stadiumResponseDTO.getCidade(), estadioEntity.getCidade());
        Assertions.assertEquals(stadiumResponseDTO.getCapacidade(), estadioEntity.getCapacidade());

        Mockito.verify(estadioRepository, Mockito.times(1)).save(Mockito.any(EstadioEntity.class));

    }

    @Test
    void atualizarEstadioComSucesso() {
        Long id = 1L;

        StadiumRequestDTO stadiumRequestDTO = new StadiumRequestDTO();
        stadiumRequestDTO.setNome("Neo Quimica Arena");
        stadiumRequestDTO.setCidade("Sao Paulo");
        stadiumRequestDTO.setCapacidade(47000);

        EstadioEntity estadioEntityExistente = new EstadioEntity(id, "Allianz Park", "São Paulo", 50000);

        EstadioEntity estadioEntityAtualizado = new EstadioEntity(id,
        stadiumRequestDTO.getNome(),
        stadiumRequestDTO.getCidade(),
        stadiumRequestDTO.getCapacidade());

        Mockito.when(estadioRepository.findById(id)).thenReturn(Optional.of(estadioEntityExistente));
        Mockito.when(estadioRepository.save(Mockito.any(EstadioEntity.class))).thenReturn(estadioEntityAtualizado);

        StadiumResponseDTO stadiumResponseDTO = estadioService.atualizarEstadio(id, stadiumRequestDTO);

        Assertions.assertNotNull(stadiumResponseDTO);
        Assertions.assertEquals(stadiumRequestDTO.getNome(), stadiumResponseDTO.getNome());
        Assertions.assertEquals(stadiumRequestDTO.getCidade(), stadiumResponseDTO.getCidade());
        Assertions.assertEquals(stadiumRequestDTO.getCapacidade(), stadiumResponseDTO.getCapacidade());

        Mockito.verify(estadioRepository).findById(id);
        Mockito.verify(estadioRepository).save(Mockito.any(EstadioEntity.class));
    }
   @Test
    void buscarEstadioPorIdComSucesso() {
        Long id = 1L;
        EstadioEntity estadioEntity = new EstadioEntity(id, "Allianz Park", "São Paulo", 50000);
        Mockito.when(estadioRepository.findById(id)).thenReturn(Optional.of(estadioEntity));

        StadiumResponseDTO resposta = estadioService.buscarPorId(id);

        Assertions.assertEquals(resposta.getNome(), estadioEntity.getNome());
        Assertions.assertEquals(resposta.getCapacidade(), estadioEntity.getCapacidade());
        Assertions.assertEquals(resposta.getCapacidade(), estadioEntity.getCapacidade());

        Mockito.verify(estadioRepository, Mockito.times(1)).findById(id);

   }
    @Test
    void buscarEstadioPorNomeComSucesso() {
        String nome = "Allianz Park";
       EstadioEntity estadio = new EstadioEntity(1l,"Allianz Park","São Paulo",50000);

        Mockito.when(estadioRepository.findByNomeIgnoreCase(nome)).thenReturn(Optional.of(estadio));

        List<StadiumResponseDTO> resultado = estadioService. buscarPorNome(nome);

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Allianz Park", resultado.get(0).getNome());
        Assertions.assertEquals("São Paulo", resultado.get(0).getCidade());
        Assertions.assertEquals(50000, resultado.get(0).getCapacidade());

        Mockito.verify(estadioRepository).findByNomeIgnoreCase(nome);
    }

   @Test
    void listarEstadioComPaginacao() {
       Pageable pageable = PageRequest.of(0, 10);
       List<EstadioEntity> lista = List.of( new EstadioEntity(1L, "Allianz Park", "São Paulo", 50000 ),
               new EstadioEntity(2L, "Maracanã", "Rio de Janeiro", 55000));

       Mockito.when(estadioRepository.findAll(pageable)).thenReturn(new PageImpl<>(lista));

       Page<StadiumResponseDTO> pagina = estadioService.listar(pageable);

       Assertions.assertEquals(2, pagina.getContent ().size());
       Assertions.assertEquals ("Allianz Park", pagina.getContent().get(0).getNome());
       Assertions.assertEquals ("Maracanã", pagina.getContent().get(1).getNome());

       Mockito.verify(estadioRepository, Mockito.times(1)).findAll(pageable);
   }


   @Test
    void buscarEstadioPorIdDeveLancarExcecao() {
       Mockito.when(estadioRepository.findById(999L)).thenReturn(Optional.empty());

       EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
           estadioService.buscarPorId(999L);
       });

       assertEquals("Estádio não encontrado!", exception.getMessage());
   }

    @Test
    void listarEstadioPorCidade() {
        Pageable pageable = PageRequest.of(0, 10);
        String cidade = "São Paulo";

        EstadioEntity estadioEntity1 = new EstadioEntity(1L, "Allianz Park", "São Paulo", 50000);
        EstadioEntity estadioEntity2 = new EstadioEntity(2L, "Morumbis", "São Paulo", 55000);
        List<EstadioEntity> lista = List.of(estadioEntity1, estadioEntity2);

        Page<EstadioEntity> page = new PageImpl<>(lista);

        Mockito.when(estadioRepository.findByCidadeContainingIgnoreCase(cidade, pageable)).thenReturn(page);

        Page<StadiumResponseDTO> resultado = estadioService.listarPorCidade(cidade,pageable);

        Assertions.assertEquals(2, resultado.getContent().size());
        Assertions.assertEquals("Allianz Park", resultado.getContent().get(0).getNome());
        Assertions.assertEquals("Morumbis", resultado.getContent().get(1).getNome());

        Mockito.verify(estadioRepository).findByCidadeContainingIgnoreCase(cidade, pageable);
    }

}

