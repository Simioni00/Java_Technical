package com.example.demo.repository;

import com.example.demo.entity.Pessoa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PessoaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    public void testFindById() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");
        pessoa.setDataInicial(LocalDateTime.now());
        pessoa.setDataFinal(LocalDateTime.now().plusDays(1));
        entityManager.persist(pessoa);
        entityManager.flush();

        Optional<Pessoa> found = pessoaRepository.findById(pessoa.getId());

        assertTrue(found.isPresent());
        assertEquals("João", found.get().getNome());
    }

    @Test
    public void testFindByIdNotFound() {
        Optional<Pessoa> found = pessoaRepository.findById(999L);
        assertFalse(found.isPresent());
    }

    @Test
    public void testSavePessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Maria");
        pessoa.setDataInicial(LocalDateTime.now());
        pessoa.setDataFinal(LocalDateTime.now().plusDays(1));

        Pessoa saved = pessoaRepository.save(pessoa);

        assertNotNull(saved.getId());
        assertEquals("Maria", saved.getNome());
    }

    @Test
    public void testDeletePessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Carlos");
        pessoa.setDataInicial(LocalDateTime.now());
        pessoa.setDataFinal(LocalDateTime.now().plusDays(1));
        entityManager.persist(pessoa);
        entityManager.flush();

        pessoaRepository.deleteById(pessoa.getId());

        Optional<Pessoa> found = pessoaRepository.findById(pessoa.getId());
        assertFalse(found.isPresent());
    }
}