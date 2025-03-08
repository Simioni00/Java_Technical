package com.example.demo.service;

import com.example.demo.dto.LaboratorioDTO;
import com.example.demo.dto.LaboratorioResponseDTO;
import com.example.demo.entity.Laboratorio;
import com.example.demo.repository.LaboratorioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LaboratorioServiceTest {

    @Mock
    private LaboratorioRepository laboratorioRepository;

    @InjectMocks
    private LaboratorioService laboratorioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLaboratorioById() {

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório 1");

        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));

        LaboratorioResponseDTO responseDTO = laboratorioService.getLaboratorioById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Laboratório 1", responseDTO.getNome());
    }

    @Test
    public void testGetLaboratorioByIdNotFound() {

        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> laboratorioService.getLaboratorioById(1L));
    }

    @Test
    public void testCreateLaboratorio() {

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório 1");

        when(laboratorioRepository.save(any(Laboratorio.class))).thenReturn(laboratorio);

        LaboratorioResponseDTO responseDTO = laboratorioService.createLaboratorio(new LaboratorioDTO());

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Laboratório 1", responseDTO.getNome());
    }

    @Test
    public void testUpdateLaboratorio() {

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório Atualizado");

        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(laboratorioRepository.save(any(Laboratorio.class))).thenReturn(laboratorio);

        LaboratorioResponseDTO responseDTO = laboratorioService.updateLaboratorio(1L, new LaboratorioDTO());

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Laboratório Atualizado", responseDTO.getNome());
    }

    @Test
    public void testDeleteLaboratorio() {

        when(laboratorioRepository.existsById(1L)).thenReturn(true);

        laboratorioService.deleteLaboratorio(1L);

        verify(laboratorioRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteLaboratorioNotFound() {

        when(laboratorioRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> laboratorioService.deleteLaboratorio(1L));
    }
}