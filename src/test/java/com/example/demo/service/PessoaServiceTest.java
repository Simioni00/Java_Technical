package com.example.demo.service;

import com.example.demo.dto.PessoaRequestDTO;
import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.entity.Pessoa;
import com.example.demo.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPessoaById() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        PessoaResponseDTO responseDTO = pessoaService.getPessoaById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("João", responseDTO.getNome());
    }

    @Test
    public void testGetPessoaByIdNotFound() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pessoaService.getPessoaById(1L));
    }

    @Test
    public void testCreatePessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Maria");

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaResponseDTO responseDTO = pessoaService.createPessoa(new PessoaRequestDTO());

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Maria", responseDTO.getNome());
    }

    @Test
    public void testUpdatePessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Carlos");

        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaResponseDTO responseDTO = pessoaService.updatePessoa(1L, new PessoaRequestDTO());

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Carlos", responseDTO.getNome());
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

        assertThrows(RuntimeException.class, () -> pessoaService.deletePessoa(1L));
    }
}