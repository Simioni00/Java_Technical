package com.example.demo.service;

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
        propriedade1.setNome("Propriedade 1");

        Propriedade propriedade2 = new Propriedade();
        propriedade2.setNome("Propriedade 2");

        when(propriedadeRepository.findAll()).thenReturn(Arrays.asList(propriedade1, propriedade2));

        List<Propriedade> result = propriedadeService.getAllPropriedades();
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

        Optional<Propriedade> result = propriedadeService.getPropriedadeById(1L);
        assertTrue(result.isPresent());
        assertEquals("Propriedade 1", result.get().getNome());
    }

    @Test
    public void testGetPropriedadeByIdNotFound() {
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Propriedade> result = propriedadeService.getPropriedadeById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreatePropriedade() {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome("Propriedade 1");

        when(propriedadeRepository.save(propriedade)).thenReturn(propriedade);

        Propriedade result = propriedadeService.createPropriedade(propriedade);
        assertEquals("Propriedade 1", result.getNome());
    }

    @Test
    public void testUpdatePropriedade() {
        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Propriedade 1");

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(propriedadeRepository.save(propriedade)).thenReturn(propriedade);

        Propriedade result = propriedadeService.updatePropriedade(1L, propriedade);
        assertEquals("Propriedade 1", result.getNome());
    }

    @Test
    public void testUpdatePropriedadeNotFound() {
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> propriedadeService.updatePropriedade(1L, new Propriedade()));
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