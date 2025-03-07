package com.example.demo.service;

import com.example.demo.dto.LaboratorioFilterDTO;
import com.example.demo.dto.LaboratorioResponseDTO;
import com.example.demo.entity.Laboratorio;
import com.example.demo.repository.LaboratorioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LaboratorioServiceTest {

    @Mock
    private LaboratorioRepository laboratorioRepository;

    @InjectMocks
    private LaboratorioService laboratorioService;

    @Test
    public void testGetAllLaboratorios() {
        Laboratorio laboratorio1 = new Laboratorio();
        laboratorio1.setId(1L);
        laboratorio1.setNome("Laboratório 1");

        Laboratorio laboratorio2 = new Laboratorio();
        laboratorio2.setId(2L);
        laboratorio2.setNome("Laboratório 2");

        when(laboratorioRepository.findAll()).thenReturn(Arrays.asList(laboratorio1, laboratorio2));

        List<Laboratorio> result = laboratorioService.getAllLaboratorios();
        assertEquals(2, result.size());
        assertEquals("Laboratório 1", result.get(0).getNome());
        assertEquals("Laboratório 2", result.get(1).getNome());
    }

    @Test
    public void testGetLaboratorioById() {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório 1");

        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));

        Optional<Laboratorio> result = laboratorioService.getLaboratorioById(1L);
        assertTrue(result.isPresent());
        assertEquals("Laboratório 1", result.get().getNome());
    }

    @Test
    public void testGetLaboratorioByIdNotFound() {
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Laboratorio> result = laboratorioService.getLaboratorioById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateLaboratorio() {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome("Laboratório 1");

        when(laboratorioRepository.save(laboratorio)).thenReturn(laboratorio);

        Laboratorio result = laboratorioService.createLaboratorio(laboratorio);
        assertEquals("Laboratório 1", result.getNome());
    }

    @Test
    public void testUpdateLaboratorio() {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório 1");

        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(laboratorioRepository.save(laboratorio)).thenReturn(laboratorio);

        Laboratorio result = laboratorioService.updateLaboratorio(1L, laboratorio);
        assertEquals("Laboratório 1", result.getNome());
    }

    @Test
    public void testUpdateLaboratorioNotFound() {
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> laboratorioService.updateLaboratorio(1L, new Laboratorio()));
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

    @Test
    public void testFindLaboratoriosComFiltros() {
        LaboratorioFilterDTO filtros = new LaboratorioFilterDTO();
        filtros.setDataInicialStart(LocalDateTime.now().minusDays(1));
        filtros.setDataInicialEnd(LocalDateTime.now().plusDays(1));
        filtros.setQuantidadeMinimaPessoas(1);

        LaboratorioResponseDTO responseDTO = new LaboratorioResponseDTO(1L, "LABORATÓRIO 1", 2L);
        when(laboratorioRepository.findLaboratoriosComFiltros(
                filtros.getDataInicialStart(),
                filtros.getDataInicialEnd(),
                null,
                null,
                null,
                filtros.getQuantidadeMinimaPessoas()))
                .thenReturn(List.of(responseDTO));

        List<LaboratorioResponseDTO> result = laboratorioService.findLaboratoriosComFiltros(filtros);
        assertEquals(1, result.size());
        assertEquals("LABORATÓRIO 1", result.get(0).getNome());
    }
}