package com.example.demo.repository;

import com.example.demo.entity.Propriedade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PropriedadeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Test
    public void testFindById() {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome("Fazenda Feliz");
        entityManager.persist(propriedade);
        entityManager.flush();

        Optional<Propriedade> found = propriedadeRepository.findById(propriedade.getId());

        assertTrue(found.isPresent());
        assertEquals("Fazenda Feliz", found.get().getNome());
    }

    @Test
    public void testFindByIdNotFound() {
        Optional<Propriedade> found = propriedadeRepository.findById(999L);
        assertFalse(found.isPresent());
    }

    @Test
    public void testSavePropriedade() {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome("Fazenda Feliz");

        Propriedade saved = propriedadeRepository.save(propriedade);

        assertNotNull(saved.getId());
        assertEquals("Fazenda Feliz", saved.getNome());
    }

    @Test
    public void testDeletePropriedade() {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome("Fazenda Feliz");
        entityManager.persist(propriedade);
        entityManager.flush();

        propriedadeRepository.deleteById(propriedade.getId());

        Optional<Propriedade> found = propriedadeRepository.findById(propriedade.getId());
        assertFalse(found.isPresent());
    }
}