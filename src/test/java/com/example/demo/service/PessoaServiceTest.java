/*package com.example.demo.service;

import com.example.demo.dto.LaboratorioDTO;
import com.example.demo.dto.PessoaRequestDTO;
import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.dto.PropriedadeDTO;
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
        // Criação do DTO de entrada
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setNome("João Silva");
        pessoaRequestDTO.setDataInicial(LocalDateTime.now());
        pessoaRequestDTO.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoaRequestDTO.setObservacoes("Observação teste");
        pessoaRequestDTO.setPropriedadeId(1L);
        pessoaRequestDTO.setLaboratorioId(1L);

        // Mocks das entidades relacionadas
        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Fazenda Feliz");

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório Central");

        // Configuração dos mocks
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocation -> {
            Pessoa pessoa = invocation.getArgument(0);
            return pessoa;
        });

        // Execução do método
        PessoaResponseDTO result = pessoaService.createPessoa(pessoaRequestDTO);

        // Verificações
        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
        assertEquals(1L, result.getInfosPropriedade().getId());
        assertEquals("Fazenda Feliz", result.getInfosPropriedade().getNome());
        assertEquals(1L, result.getLaboratorio().getId());
        assertEquals("Laboratório Central", result.getLaboratorio().getNome());
    }

    @Test
    public void testCreatePessoaPropriedadeNotFound() {
        // Criação do DTO de entrada
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setPropriedadeId(1L);

        // Configuração do mock para propriedade não encontrada
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificação da exceção
        assertThrows(ResponseStatusException.class, () -> pessoaService.createPessoa(pessoaRequestDTO));
    }

    @Test
    public void testCreatePessoaLaboratorioNotFound() {
        // Criação do DTO de entrada
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setPropriedadeId(1L);
        pessoaRequestDTO.setLaboratorioId(1L);

        // Mocks
        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);

        // Configuração dos mocks
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificação da exceção
        assertThrows(ResponseStatusException.class, () -> pessoaService.createPessoa(pessoaRequestDTO));
    }

    @Test
    public void testUpdatePessoa() {
        // Criação do DTO de entrada
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setNome("João Silva");
        pessoaRequestDTO.setDataInicial(LocalDateTime.now());
        pessoaRequestDTO.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoaRequestDTO.setObservacoes("Observação teste");
        pessoaRequestDTO.setPropriedadeId(1L);
        pessoaRequestDTO.setLaboratorioId(1L);

        // Mocks das entidades relacionadas
        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Fazenda Feliz");

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório Central");

        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(1L);
        pessoaExistente.setNome("Nome Antigo");
        pessoaExistente.setDataInicial(LocalDateTime.now().minusDays(1));
        pessoaExistente.setDataFinal(LocalDateTime.now());

        // Configuração dos mocks
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoaExistente));
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execução do método
        PessoaResponseDTO result = pessoaService.updatePessoa(1L, pessoaRequestDTO);

        // Verificações
        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
        assertEquals(1L, result.getInfosPropriedade().getId());
        assertEquals("Fazenda Feliz", result.getInfosPropriedade().getNome());
        assertEquals(1L, result.getLaboratorio().getId());
        assertEquals("Laboratório Central", result.getLaboratorio().getNome());
    }

    @Test
    public void testUpdatePessoaNotFound() {
        // Criação do DTO de entrada
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();

        // Configuração do mock para pessoa não encontrada
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificação da exceção
        assertThrows(ResponseStatusException.class, () -> pessoaService.updatePessoa(1L, pessoaRequestDTO));
    }

    @Test
    public void testDeletePessoa() {
        // Configuração do mock para pessoa existente
        when(pessoaRepository.existsById(1L)).thenReturn(true);

        // Execução do método
        pessoaService.deletePessoa(1L);

        // Verificação da exclusão
        verify(pessoaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletePessoaNotFound() {
        // Configuração do mock para pessoa não encontrada
        when(pessoaRepository.existsById(1L)).thenReturn(false);

        // Verificação da exceção
        assertThrows(ResponseStatusException.class, () -> pessoaService.deletePessoa(1L));
    }
}*/