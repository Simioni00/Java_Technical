package com.example.demo.service;

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
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setNome("João Silva");
        pessoaRequestDTO.setDataInicial(LocalDateTime.now());
        pessoaRequestDTO.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoaRequestDTO.setObservacoes("Observação teste");

        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setId(1L);
        propriedadeDTO.setNome("Fazenda Feliz");
        pessoaRequestDTO.setInfosPropriedade(propriedadeDTO);

        LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
        laboratorioDTO.setId(1L);
        laboratorioDTO.setNome("Laboratório Central");
        pessoaRequestDTO.setLaboratorio(laboratorioDTO);

        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Fazenda Feliz");

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório Central");

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocation -> {
            Pessoa pessoa = invocation.getArgument(0);
            return pessoa;
        });

        PessoaResponseDTO result = pessoaService.createPessoa(pessoaRequestDTO);
        assertEquals("João Silva", result.getNome());
        assertEquals(1L, result.getInfosPropriedade().getId());
        assertEquals(1L, result.getLaboratorio().getId());
    }

    @Test
    public void testCreatePessoaPropriedadeNotFound() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setId(1L);
        pessoaRequestDTO.setInfosPropriedade(propriedadeDTO);

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> pessoaService.createPessoa(pessoaRequestDTO));
    }

    @Test
    public void testCreatePessoaLaboratorioNotFound() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setId(1L);
        pessoaRequestDTO.setInfosPropriedade(propriedadeDTO);

        LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
        laboratorioDTO.setId(1L);
        pessoaRequestDTO.setLaboratorio(laboratorioDTO);

        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);

        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> pessoaService.createPessoa(pessoaRequestDTO));
    }

    @Test
    public void testUpdatePessoa() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setNome("João Silva");
        pessoaRequestDTO.setDataInicial(LocalDateTime.now());
        pessoaRequestDTO.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoaRequestDTO.setObservacoes("Observação teste");

        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setId(1L);
        propriedadeDTO.setNome("Fazenda Feliz");
        pessoaRequestDTO.setInfosPropriedade(propriedadeDTO);

        LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
        laboratorioDTO.setId(1L);
        laboratorioDTO.setNome("Laboratório Central");
        pessoaRequestDTO.setLaboratorio(laboratorioDTO);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Nome Antigo");
        pessoa.setDataInicial(LocalDateTime.now().minusDays(1));
        pessoa.setDataFinal(LocalDateTime.now());

        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Fazenda Feliz");

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório Central");

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(propriedadeRepository.findById(1L)).thenReturn(Optional.of(propriedade));
        when(laboratorioRepository.findById(1L)).thenReturn(Optional.of(laboratorio));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PessoaResponseDTO result = pessoaService.updatePessoa(1L, pessoaRequestDTO);
        assertEquals("João Silva", result.getNome());
        assertEquals(1L, result.getInfosPropriedade().getId());
        assertEquals(1L, result.getLaboratorio().getId());
    }

    @Test
    public void testUpdatePessoaNotFound() {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();

        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> pessoaService.updatePessoa(1L, pessoaRequestDTO));
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