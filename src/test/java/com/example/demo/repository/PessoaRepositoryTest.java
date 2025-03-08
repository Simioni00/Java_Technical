package com.example.demo.repository;

import com.example.demo.entity.Pessoa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    public void testSavePessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("John Doe");
        pessoa.setDataInicial(LocalDateTime.now());
        pessoa.setDataFinal(LocalDateTime.now().plusDays(1));

        Pessoa savedPessoa = pessoaRepository.save(pessoa);
        assertNotNull(savedPessoa.getId());
        assertEquals("John Doe", savedPessoa.getNome());
    }

    @Test
    public void testFindById() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("John Doe");
        pessoaRepository.save(pessoa);

        Optional<Pessoa> foundPessoa = pessoaRepository.findById(pessoa.getId());
        assertTrue(foundPessoa.isPresent());
        assertEquals("John Doe", foundPessoa.get().getNome());
    }

    @Test
    public void testDeletePessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("John Doe");
        pessoaRepository.save(pessoa);

        pessoaRepository.deleteById(pessoa.getId());
        Optional<Pessoa> deletedPessoa = pessoaRepository.findById(pessoa.getId());
        assertFalse(deletedPessoa.isPresent());
    }
}