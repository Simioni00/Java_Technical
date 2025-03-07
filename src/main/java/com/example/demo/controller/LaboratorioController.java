package com.example.demo.controller;

import com.example.demo.dto.LaboratorioFilterDTO;
import com.example.demo.dto.LaboratorioResponseDTO;
import com.example.demo.entity.Laboratorio;
import com.example.demo.service.LaboratorioService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratorios")
public class LaboratorioController {

    private final LaboratorioService laboratorioService;

    public LaboratorioController(LaboratorioService laboratorioService) {
        this.laboratorioService = laboratorioService;
    }

    @GetMapping
    public ResponseEntity<List<Laboratorio>> getAllLaboratorios() {
        List<Laboratorio> laboratorios = laboratorioService.getAllLaboratorios();
        return ResponseEntity.ok(laboratorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Laboratorio> getLaboratorioById(@PathVariable Long id) {
        return laboratorioService.getLaboratorioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Laboratorio> createLaboratorio(@Valid @RequestBody Laboratorio laboratorio) {
        Laboratorio createdLaboratorio = laboratorioService.createLaboratorio(laboratorio);
        return ResponseEntity.ok(createdLaboratorio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Laboratorio> updateLaboratorio(@PathVariable Long id,
            @Valid @RequestBody Laboratorio laboratorio) {
        Laboratorio updatedLaboratorio = laboratorioService.updateLaboratorio(id, laboratorio);
        return updatedLaboratorio != null ? ResponseEntity.ok(updatedLaboratorio) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaboratorio(@PathVariable Long id) {
        laboratorioService.deleteLaboratorio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtros")
    public ResponseEntity<List<LaboratorioResponseDTO>> getLaboratoriosComFiltros(@ModelAttribute LaboratorioFilterDTO filtros) {
        List<LaboratorioResponseDTO> laboratorios = laboratorioService.findLaboratoriosComFiltros(filtros);
        return ResponseEntity.ok(laboratorios);
    }
}