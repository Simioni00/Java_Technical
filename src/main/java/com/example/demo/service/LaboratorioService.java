package com.example.demo.service;

import com.example.demo.dto.LaboratorioDTO;
import com.example.demo.dto.LaboratorioFilterDTO;
import com.example.demo.dto.LaboratorioResponseDTO;
import com.example.demo.entity.Laboratorio;
import com.example.demo.repository.LaboratorioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;

    public LaboratorioService(LaboratorioRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }

    public List<LaboratorioResponseDTO> getAllLaboratorios() {
        return laboratorioRepository.findAll().stream()
                .map(laboratorio -> new LaboratorioResponseDTO(laboratorio.getId(), laboratorio.getNome(),
                        (long) laboratorio.getPessoas().size()))
                .collect(Collectors.toList());
    }

    public LaboratorioResponseDTO getLaboratorioById(Long id) {
        Laboratorio laboratorio = laboratorioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Laboratório não encontrado"));
        return new LaboratorioResponseDTO(laboratorio.getId(), laboratorio.getNome(),
                (long) laboratorio.getPessoas().size());
    }

    public LaboratorioResponseDTO createLaboratorio(LaboratorioDTO laboratorioDTO) {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setNome(laboratorioDTO.getNome());

        Laboratorio savedLaboratorio = laboratorioRepository.save(laboratorio);
        return new LaboratorioResponseDTO(savedLaboratorio.getId(), savedLaboratorio.getNome(),
                (long) savedLaboratorio.getPessoas().size());
    }

    public LaboratorioResponseDTO updateLaboratorio(Long id, LaboratorioDTO laboratorioDTO) {
        Laboratorio laboratorio = laboratorioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Laboratório não encontrado"));

        laboratorio.setNome(laboratorioDTO.getNome());
        Laboratorio updatedLaboratorio = laboratorioRepository.save(laboratorio);

        return new LaboratorioResponseDTO(updatedLaboratorio.getId(), updatedLaboratorio.getNome(),
                (long) updatedLaboratorio.getPessoas().size());
    }

    public void deleteLaboratorio(Long id) {
        if (!laboratorioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Laboratório não encontrado");
        }
        laboratorioRepository.deleteById(id);
    }

    public List<LaboratorioResponseDTO> findLaboratoriosComFiltros(LaboratorioFilterDTO filtros) {
        List<LaboratorioResponseDTO> laboratorios = laboratorioRepository.findLaboratoriosComFiltros(
                filtros.getDataInicialStart(),
                filtros.getDataInicialEnd(),
                filtros.getDataFinalStart(),
                filtros.getDataFinalEnd(),
                filtros.getObservacoes(),
                filtros.getQuantidadeMinimaPessoas());

        return laboratorios != null ? laboratorios : Collections.emptyList();
    }
}