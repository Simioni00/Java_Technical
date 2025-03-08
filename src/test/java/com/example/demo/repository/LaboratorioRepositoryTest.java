package com.example.demo.repository;

import com.example.demo.entity.Laboratorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LaboratorioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Test
    public void testFindById() {

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome("Laboratório 1");
        entityManager.persist(laboratorio);
        entityManager.flush();

        Optional<Laboratorio> found = laboratorioRepository.findById(laboratorio.getId());

        assertTrue(found.isPresent());
        assertEquals(laboratorio.getNome(), found.get().getNome());
    }

    @Test
    public void testFindByIdNotFound() {

        Optional<Laboratorio> found = laboratorioRepository.findById(999L);

        assertFalse(found.isPresent());
    }

    @Test
    public void testSaveLaboratorio() {

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome("Laboratório 2");

        Laboratorio saved = laboratorioRepository.save(laboratorio);

        assertNotNull(saved.getId());
        assertEquals(laboratorio.getNome(), saved.getNome());
    }

    @Test
    public void testDeleteLaboratorio() {

        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome("Laboratório 3");
        entityManager.persist(laboratorio);
        entityManager.flush();

        laboratorioRepository.deleteById(laboratorio.getId());

        Optional<Laboratorio> found = laboratorioRepository.findById(laboratorio.getId());
        assertFalse(found.isPresent());
    }
}