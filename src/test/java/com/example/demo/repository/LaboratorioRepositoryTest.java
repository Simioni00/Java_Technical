package com.example.demo.repository;

import com.example.demo.entity.Laboratorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LaboratorioRepositoryTest {

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Test
    public void testSaveLaboratorio() {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome("Laboratório 1");

        Laboratorio savedLaboratorio = laboratorioRepository.save(laboratorio);
        assertNotNull(savedLaboratorio.getId());
        assertEquals("Laboratório 1", savedLaboratorio.getNome());
    }

    @Test
    public void testFindById() {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome("Laboratório 1");
        laboratorioRepository.save(laboratorio);

        Optional<Laboratorio> foundLaboratorio = laboratorioRepository.findById(laboratorio.getId());
        assertTrue(foundLaboratorio.isPresent());
        assertEquals("Laboratório 1", foundLaboratorio.get().getNome());
    }

    @Test
    public void testDeleteLaboratorio() {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome("Laboratório 1");
        laboratorioRepository.save(laboratorio);

        laboratorioRepository.deleteById(laboratorio.getId());
        Optional<Laboratorio> deletedLaboratorio = laboratorioRepository.findById(laboratorio.getId());
        assertFalse(deletedLaboratorio.isPresent());
    }
}