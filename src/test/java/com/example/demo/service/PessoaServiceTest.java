package com.example.demo.service;

import com.example.demo.dto.PessoaDTO;
import com.example.demo.entity.Laboratorio;
import com.example.demo.entity.Pessoa;
import com.example.demo.entity.Propriedade;
import com.example.demo.repository.LaboratorioRepository;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.repository.PropriedadeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PropriedadeRepository propriedadeRepository;

    @Mock
    private LaboratorioRepository laboratorioRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @Test
    public void testCreatePessoa() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("João Silva");
        pessoaDTO.setDataInicial(LocalDateTime.now());
        pessoaDTO.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoaDTO.setObservacoes("Observação teste");
        pessoaDTO.setPropriedadeId(1L);
        pessoaDTO.setLaboratorioId(1L);

        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pessoa result = pessoaService.createPessoa(pessoaDTO);
        assertEquals("João Silva", result.getNome());
        assertEquals(propriedade, result.getPropriedade());
        assertEquals(laboratorio, result.getLaboratorio());
    }

    @Test
    public void testCreatePessoaPropriedadeNotFound() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setPropriedadeId(1L);

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> pessoaService.createPessoa(pessoaDTO));
    }

    @Test
    public void testCreatePessoaLaboratorioNotFound() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setPropriedadeId(1L);
        pessoaDTO.setLaboratorioId(1L);

        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> pessoaService.createPessoa(pessoaDTO));
    }

    @Test
    public void testUpdatePessoa() {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("John Doe");
        pessoaDTO.setDataInicial(LocalDateTime.now());
        pessoaDTO.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoaDTO.setObservacoes("Observação teste");
        pessoaDTO.setPropriedadeId(1L);
        pessoaDTO.setLaboratorioId(1L);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Nome Antigo");
        pessoa.setDataInicial(LocalDateTime.now().minusDays(1));
        pessoa.setDataFinal(LocalDateTime.now());

        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pessoa result = pessoaService.updatePessoa(1L, pessoaDTO);
        assertEquals("João Silva", result.getNome());
        assertEquals(propriedade, result.getPropriedade());
        assertEquals(laboratorio, result.getLaboratorio());
    }

    @Test
    public void testUpdatePessoaNotFound() {
        PessoaDTO pessoaDTO = new PessoaDTO();

        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> pessoaService.updatePessoa(1L, pessoaDTO));
    }

    @Test
    public void testDeletePessoa() {
        when(pessoaRepository.existsById(1L)).thenReturn(true);
        pessoaService.deletePessoa(1L);
        verify(pessoaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletePessoaNotFound() {
        when(pessoaRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> pessoaService.deletePessoa(1L));
    }
}