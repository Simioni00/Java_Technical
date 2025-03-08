package com.example.demo.repository;

import com.example.demo.entity.Propriedade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PropriedadeRepositoryTest {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Test
    public void testSavePropriedade() {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome("Propriedade 1");

        Propriedade savedPropriedade = propriedadeRepository.save(propriedade);
        assertNotNull(savedPropriedade.getId());
        assertEquals("Propriedade 1", savedPropriedade.getNome());
    }

    @Test
    public void testFindById() {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome("Propriedade 1");
        propriedadeRepository.save(propriedade);

        Optional<Propriedade> foundPropriedade = propriedadeRepository.findById(propriedade.getId());
        assertTrue(foundPropriedade.isPresent());
        assertEquals("Propriedade 1", foundPropriedade.get().getNome());
    }

    @Test
    public void testDeletePropriedade() {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome("Propriedade 1");
        propriedadeRepository.save(propriedade);

        propriedadeRepository.deleteById(propriedade.getId());
        Optional<Propriedade> deletedPropriedade = propriedadeRepository.findById(propriedade.getId());
        assertFalse(deletedPropriedade.isPresent());
    }
}