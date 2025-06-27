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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StadiumServiceTest {

    @InjectMocks
    private StadiumService stadiumService;

    @Mock
    private StadiumRepository stadiumRepository;


    @Test
    void registerStadiumSuccessfully() {
        StadiumRequestDTO stadiumRequestDTO = new StadiumRequestDTO();

        StadiumEntity stadiumEntity = new StadiumEntity();
        stadiumEntity.setName("Allianz Park");
        stadiumEntity.setCity("Sao Paulo");
        stadiumEntity.setCapacity(50000);

        Mockito.when(stadiumRepository.save(Mockito.any(StadiumEntity.class))).thenReturn(stadiumEntity);

        StadiumResponseDTO stadiumResponseDTO = stadiumService.registerStadium(stadiumRequestDTO);

        Assertions.assertNotNull(stadiumResponseDTO);

        Assertions.assertEquals(stadiumResponseDTO.getName(), stadiumEntity.getName());
        Assertions.assertEquals(stadiumResponseDTO.getCity(), stadiumEntity.getCity());
        Assertions.assertEquals(stadiumResponseDTO.getCapacity(), stadiumEntity.getCapacity());

        Mockito.verify(stadiumRepository, Mockito.times(1)).save(Mockito.any(StadiumEntity.class));

    }

    @Test
    void updateStadiumSuccessfully() {
        Long id = 1L;

        StadiumRequestDTO stadiumRequestDTO = new StadiumRequestDTO();
        stadiumRequestDTO.setName("Neo Quimica Arena");
        stadiumRequestDTO.setCity("Sao Paulo");
        stadiumRequestDTO.setCapacity(47000);

       StadiumEntity stadiumEntityExist = new StadiumEntity(id,"Allianz Park","São Paulo", 50000,null);
       StadiumEntity stadiumEntityUpdated = new StadiumEntity(id,"Neo Quimica Arena", "São Paulo", 47000,null);


       Mockito.when(stadiumRepository.findById(id)).thenReturn(Optional.of(stadiumEntityExist));

       Mockito.when(stadiumRepository.save(Mockito.any(StadiumEntity.class))).thenReturn(stadiumEntityUpdated);

       StadiumResponseDTO stadiumResponseDTO = stadiumService.updateStadium(id, stadiumRequestDTO);

        Assertions.assertNotNull(stadiumResponseDTO);
        Assertions.assertEquals("Neo Quimica Arena", stadiumResponseDTO.getName());
        Assertions.assertEquals("São Paulo",stadiumResponseDTO.getCity());
        Assertions.assertEquals( 47000,stadiumResponseDTO.getCapacity());


    }
   @Test
    void findStadiumByIdSuccessfully() {
        Long id = 1L;
        StadiumEntity stadiumEntity = new StadiumEntity();
        Mockito.when(stadiumRepository.findById(id)).thenReturn(Optional.of(stadiumEntity));

        StadiumResponseDTO response = stadiumService.findById(id);

        Assertions.assertEquals(response.getName(), stadiumEntity.getName());
        Assertions.assertEquals(response.getCapacity(), stadiumEntity.getCapacity());
        Assertions.assertEquals(response.getCapacity(), stadiumEntity.getCapacity());

        Mockito.verify(stadiumRepository, Mockito.times(1)).findById(id);

   }
    @Test
    void findStadiumByNameSuccessfully() {
       String name = "Allianz Park";

       StadiumEntity stadium = new StadiumEntity();
       stadium.setName("Allianz Park");
       stadium.setCity("São Paulo");
       stadium.setCapacity(50000);

        Mockito.when(stadiumRepository.findByNameIgnoreCase(name)).thenReturn(Optional.of(stadium));

        List<StadiumResponseDTO> result = stadiumService. findByName(name);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Allianz Park", result.get(0).getName());
        Assertions.assertEquals("São Paulo", result.get(0).getCity());
        Assertions.assertEquals(50000, result.get(0).getCapacity());

        Mockito.verify(stadiumRepository).findByNameIgnoreCase(name);
    }

   @Test
    void listStadiumPaginatedSuccessfully() {
       Pageable pageable = PageRequest.of(0, 10);

       StadiumEntity stadiumEntity1 = new StadiumEntity();
       stadiumEntity1.setId(1L);
       stadiumEntity1.setName("Allianz Park");
       stadiumEntity1.setCity("Sao Paulo");
       stadiumEntity1.setCapacity(50000);

       StadiumEntity stadiumEntity2 = new StadiumEntity();
       stadiumEntity2.setId(2L);
       stadiumEntity2.setName("Maracanã");
       stadiumEntity2.setCity("Rio de Janeiro");
       stadiumEntity2.setCapacity(55000);

       List<StadiumEntity> list = List.of(stadiumEntity1,stadiumEntity2);
       Mockito.when(stadiumRepository.findAll(pageable)).thenReturn(new PageImpl<>(list));

       Page<StadiumResponseDTO> page = stadiumService.list(pageable);

       Assertions.assertEquals(2, page.getContent ().size());
       Assertions.assertEquals ("Allianz Park", page.getContent().get(0).getName());
       Assertions.assertEquals ("Maracanã", page.getContent().get(1).getName());

       Mockito.verify(stadiumRepository, Mockito.times(1)).findAll(pageable);
   }


   @Test
    void findStadiumByIdException() {
       Mockito.when(stadiumRepository.findById(999L)).thenReturn(Optional.empty());

       EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
           stadiumService.findById(999L);
       });

       assertEquals("Estádio não encontrado!", exception.getMessage());
   }

    @Test
    void listStadiumByCity() {
        Pageable pageable = PageRequest.of(0, 10);
        String city = "São Paulo";

        StadiumEntity stadiumEntity1 = new StadiumEntity();
        stadiumEntity1.setId(1L);
        stadiumEntity1.setName("Allianz Park");
        stadiumEntity1.setCity(city);
        stadiumEntity1.setCapacity(50000);

        StadiumEntity stadiumEntity2 = new StadiumEntity();
        stadiumEntity2.setId(2L);
        stadiumEntity2.setName("Maracanã");
        stadiumEntity2.setCity("Rio de Janeiro");
        stadiumEntity2.setCapacity(55000);

        List<StadiumEntity> list = List.of(stadiumEntity1, stadiumEntity2);
        Page<StadiumEntity> page = new PageImpl<>(list);

        Mockito.when(stadiumRepository.findByCityContainingIgnoreCase(city, pageable)).thenReturn(page);

        Page<StadiumResponseDTO> resultado = stadiumService.listByCity(city,pageable);

        Assertions.assertEquals(2, resultado.getContent().size());
        Assertions.assertEquals("Allianz Park", resultado.getContent().get(0).getName());
        Assertions.assertEquals("Maracanã", resultado.getContent().get(1).getName());

        Mockito.verify(stadiumRepository).findByCityContainingIgnoreCase(city, pageable);
    }

}

