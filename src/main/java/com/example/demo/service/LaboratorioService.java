package com.example.demo.service;

import com.example.demo.dto.LaboratorioFilterDTO;
import com.example.demo.entity.Laboratorio;
import com.example.demo.repository.LaboratorioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;

    public LaboratorioService(LaboratorioRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }

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
        return laboratorioRepository.findById(id)
                .map(laboratorio -> {
                    laboratorio.setNome(laboratorioDetails.getNome());
                    return laboratorioRepository.save(laboratorio);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Laboratório não encontrado"));
    }

    public void deleteLaboratorio(Long id) {
        if (!laboratorioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Laboratório não encontrado");
        }
        laboratorioRepository.deleteById(id);
    }

    public List<Laboratorio> findLaboratoriosComFiltros(LaboratorioFilterDTO filtros) {
        List<Laboratorio> laboratorios = laboratorioRepository.findLaboratoriosComFiltros(
                filtros.getDataInicialStart(),
                filtros.getDataInicialEnd(),
                filtros.getDataFinalStart(),
                filtros.getDataFinalEnd(),
                filtros.getObservacoes(),
                filtros.getQuantidadeMinimaPessoas());
        
        return laboratorios != null ? laboratorios : Collections.emptyList();
    }
}