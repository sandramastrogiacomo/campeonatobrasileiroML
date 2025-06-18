package com.campeonatobrasileiro.brasileirao_api.service;

import com.campeonatobrasileiro.brasileirao_api.dto.EstadioRequestDTO;
import com.campeonatobrasileiro.brasileirao_api.dto.EstadioResponseDTO;
import com.campeonatobrasileiro.brasileirao_api.entity.EstadioEntity;
import com.campeonatobrasileiro.brasileirao_api.repository.EstadioRepository;
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

@ExtendWith(MockitoExtension.class)
public class EstadioServiceTest {

    @InjectMocks
    private EstadioService estadioService;

    @Mock
    private EstadioRepository estadioRepository;
    private EstadioEntity estadio;

    @Test
    void cadastrarEstadioComSucesso() {
        EstadioRequestDTO estadioRequestDTO = new EstadioRequestDTO();
        EstadioEntity estadioEntity = new EstadioEntity(1l,"Allianz Park","São Paulo",50000);

        Mockito.when(estadioRepository.save(Mockito.any(EstadioEntity.class))).thenReturn(estadioEntity);

        EstadioResponseDTO estadioResponseDTO = estadioService.cadastrarEstadio(estadioRequestDTO);

        Assertions.assertNotNull(estadioResponseDTO);

        Assertions.assertEquals(estadioResponseDTO.getNome(), estadioEntity.getNome());
        Assertions.assertEquals(estadioResponseDTO.getCidade(), estadioEntity.getCidade());
        Assertions.assertEquals(estadioResponseDTO.getCapacidade(), estadioEntity.getCapacidade());

        Mockito.verify(estadioRepository, Mockito.times(1)).save(Mockito.any(EstadioEntity.class));

    }

    @Test
    void atualizarEstadioComSucesso() {
        Long id = 1L;

        EstadioRequestDTO estadioRequestDTO = new EstadioRequestDTO();
        estadioRequestDTO.setNome("Neo Quimica Arena");
        estadioRequestDTO.setCidade("Sao Paulo");
        estadioRequestDTO.setCapacidade(47000);

        EstadioEntity estadioEntityExistente = new EstadioEntity(id, "Allianz Park", "São Paulo", 50000);

        EstadioEntity estadioEntityAtualizado = new EstadioEntity(id,
        estadioRequestDTO.getNome(),
        estadioRequestDTO.getCidade(),
        estadioRequestDTO.getCapacidade());

        Mockito.when(estadioRepository.findById(id)).thenReturn(Optional.of(estadioEntityExistente));
        Mockito.when(estadioRepository.save(Mockito.any(EstadioEntity.class))).thenReturn(estadioEntityAtualizado);

        EstadioResponseDTO estadioResponseDTO = estadioService.atualizarEstadio(id,estadioRequestDTO);

        Assertions.assertNotNull(estadioResponseDTO);
        Assertions.assertEquals(estadioRequestDTO.getNome(), estadioResponseDTO.getNome());
        Assertions.assertEquals(estadioRequestDTO.getCidade(), estadioResponseDTO.getCidade());
        Assertions.assertEquals(estadioRequestDTO.getCapacidade(), estadioResponseDTO.getCapacidade());

        Mockito.verify(estadioRepository).findById(id);
        Mockito.verify(estadioRepository).save(Mockito.any(EstadioEntity.class));
    }
   @Test
    void buscarEstadioPorIdComSucesso() {
        Long id = 1L;
        EstadioEntity estadioEntity = new EstadioEntity(id, "Allianz Park", "São Paulo", 50000);
        Mockito.when(estadioRepository.findById(id)).thenReturn(Optional.of(estadioEntity));

        EstadioResponseDTO resposta = estadioService.buscarPorId(id);

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

        List<EstadioResponseDTO> resultado = estadioService. buscarPorNome(nome);

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

       Page<EstadioResponseDTO> pagina = estadioService.listar(pageable);

       Assertions.assertEquals(2, pagina.getContent ().size());
       Assertions.assertEquals ("Allianz Park", pagina.getContent().get(0).getNome());
       Assertions.assertEquals ("Maracanã", pagina.getContent().get(1).getNome());

       Mockito.verify(estadioRepository, Mockito.times(1)).findAll(pageable);
   }

}
