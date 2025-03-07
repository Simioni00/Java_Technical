package com.example.demo.service;

import com.example.demo.entity.Laboratorio;
import com.example.demo.repository.LaboratorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratorioService {

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    public List<Laboratorio> getAllLaboratorios() {
        return laboratorioRepository.findAll();
    }

    public Optional<Laboratorio> getLaboratorioById(Long id) {
        return laboratorioRepository.findById(id);
    }

    public Laboratorio createLaboratorio(Laboratorio laboratorio) {
        return laboratorioRepository.save(laboratorio);
    }

    public Laboratorio updateLaboratorio(Long id, Laboratorio laboratorioDetails) {
        Optional<Laboratorio> optionalLaboratorio = laboratorioRepository.findById(id);
        if (optionalLaboratorio.isPresent()) {
            Laboratorio laboratorio = optionalLaboratorio.get();
            laboratorio.setNome(laboratorioDetails.getNome());
            return laboratorioRepository.save(laboratorio);
        }
        return null;
    }

    public void deleteLaboratorio(Long id) {
        laboratorioRepository.deleteById(id);
    }
}