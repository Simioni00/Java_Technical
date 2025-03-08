package com.example.demo.service;

import com.example.demo.dto.PropriedadeDTO;
import com.example.demo.dto.PropriedadeResponseDTO;
import com.example.demo.entity.Propriedade;
import com.example.demo.repository.PropriedadeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropriedadeServiceTest {

    @Mock
    private PropriedadeRepository propriedadeRepository;

    @InjectMocks
    private PropriedadeService propriedadeService;

    @Test
    public void testGetAllPropriedades() {
        Propriedade propriedade1 = new Propriedade();
        propriedade1.setId(1L);
        propriedade1.setNome("Propriedade 1");

        Propriedade propriedade2 = new Propriedade();
        propriedade2.setId(2L);
        propriedade2.setNome("Propriedade 2");

        when(propriedadeRepository.findAll()).thenReturn(Arrays.asList(propriedade1, propriedade2));

        List<PropriedadeResponseDTO> result = propriedadeService.getAllPropriedades();
        assertEquals(2, result.size());
        assertEquals("Propriedade 1", result.get(0).getNome());
        assertEquals("Propriedade 2", result.get(1).getNome());
    }

    @Test
    public void testGetPropriedadeById() {
        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Propriedade 1");

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));

        PropriedadeResponseDTO result = propriedadeService.getPropriedadeById(1L);
        assertNotNull(result);
        assertEquals("Propriedade 1", result.getNome());
    }

    @Test
    public void testGetPropriedadeByIdNotFound() {
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> propriedadeService.getPropriedadeById(1L));
    }

    @Test
    public void testCreatePropriedade() {
        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setNome("Propriedade 1");

        Propriedade propriedade = new Propriedade();
        propriedade.setNome(propriedadeDTO.getNome());

        when(propriedadeRepository.save(any(Propriedade.class))).thenReturn(propriedade);

        PropriedadeResponseDTO result = propriedadeService.createPropriedade(propriedadeDTO);
        assertEquals("Propriedade 1", result.getNome());
    }

    @Test
    public void testUpdatePropriedade() {
        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setNome("Propriedade Atualizada");

        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Propriedade 1");

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(propriedadeRepository.save(any(Propriedade.class))).thenReturn(propriedade);

        PropriedadeResponseDTO result = propriedadeService.updatePropriedade(1L, propriedadeDTO);
        assertEquals("Propriedade Atualizada", result.getNome());
    }

    @Test
    public void testUpdatePropriedadeNotFound() {
        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setNome("Propriedade Atualizada");

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> propriedadeService.updatePropriedade(1L, propriedadeDTO));
    }

    @Test
    public void testDeletePropriedade() {
        when(propriedadeRepository.existsById(1L)).thenReturn(true);
        propriedadeService.deletePropriedade(1L);
        verify(propriedadeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletePropriedadeNotFound() {
        when(propriedadeRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> propriedadeService.deletePropriedade(1L));
    }
}