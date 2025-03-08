package com.example.demo.service;

import com.example.demo.dto.PropriedadeDTO;
import com.example.demo.dto.PropriedadeResponseDTO;
import com.example.demo.entity.Propriedade;
import com.example.demo.repository.PropriedadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PropriedadeServiceTest {

    @Mock
    private PropriedadeRepository propriedadeRepository;

    @InjectMocks
    private PropriedadeService propriedadeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPropriedadeById() {
        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Fazenda Feliz");

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));

        PropriedadeResponseDTO responseDTO = propriedadeService.getPropriedadeById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Fazenda Feliz", responseDTO.getNome());
    }

    @Test
    public void testGetPropriedadeByIdNotFound() {
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> propriedadeService.getPropriedadeById(1L));
    }

    @Test
    public void testCreatePropriedade() {
        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Fazenda Feliz");

        when(propriedadeRepository.save(any(Propriedade.class))).thenReturn(propriedade);

        PropriedadeResponseDTO responseDTO = propriedadeService.createPropriedade(new PropriedadeDTO());

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Fazenda Feliz", responseDTO.getNome());
    }

    @Test
    public void testUpdatePropriedade() {
        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Fazenda Feliz Atualizada");

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(propriedadeRepository.save(any(Propriedade.class))).thenReturn(propriedade);

        PropriedadeResponseDTO responseDTO = propriedadeService.updatePropriedade(1L, new PropriedadeDTO());

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Fazenda Feliz Atualizada", responseDTO.getNome());
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

        assertThrows(RuntimeException.class, () -> propriedadeService.deletePropriedade(1L));
    }
}