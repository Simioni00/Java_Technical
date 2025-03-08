package com.example.demo.service;

import com.example.demo.dto.LaboratorioDTO;
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

        when(laboratorioRepository.findAll()).thenReturn(List.of(laboratorio1, laboratorio2));

        List<LaboratorioResponseDTO> result = laboratorioService.getAllLaboratorios();
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

        LaboratorioResponseDTO result = laboratorioService.getLaboratorioById(1L);
        assertNotNull(result);
        assertEquals("Laboratório 1", result.getNome());
    }

    @Test
    public void testGetLaboratorioByIdNotFound() {
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> laboratorioService.getLaboratorioById(1L));
    }

    @Test
    public void testCreateLaboratorio() {
        LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
        laboratorioDTO.setNome("Laboratório 1");

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome(laboratorioDTO.getNome());

        when(laboratorioRepository.save(any(Laboratorio.class))).thenReturn(laboratorio);

        LaboratorioResponseDTO result = laboratorioService.createLaboratorio(laboratorioDTO);
        assertNotNull(result);
        assertEquals("Laboratório 1", result.getNome());
    }

    @Test
    public void testUpdateLaboratorio() {
        LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
        laboratorioDTO.setNome("Laboratório Atualizado");

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório 1");

        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(laboratorioRepository.save(any(Laboratorio.class))).thenReturn(laboratorio);

        LaboratorioResponseDTO result = laboratorioService.updateLaboratorio(1L, laboratorioDTO);
        assertNotNull(result);
        assertEquals("Laboratório Atualizado", result.getNome());
    }

    @Test
    public void testUpdateLaboratorioNotFound() {
        LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
        laboratorioDTO.setNome("Laboratório Atualizado");

        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> laboratorioService.updateLaboratorio(1L, laboratorioDTO));
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