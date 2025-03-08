package com.example.demo.controller;

import com.example.demo.dto.LaboratorioDTO;
import com.example.demo.dto.LaboratorioFilterDTO;
import com.example.demo.dto.LaboratorioResponseDTO;
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
    public ResponseEntity<List<LaboratorioResponseDTO>> getAllLaboratorios() {
        List<LaboratorioResponseDTO> laboratorios = laboratorioService.getAllLaboratorios();
        return ResponseEntity.ok(laboratorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratorioResponseDTO> getLaboratorioById(@PathVariable Long id) {
        LaboratorioResponseDTO laboratorio = laboratorioService.getLaboratorioById(id);
        return ResponseEntity.ok(laboratorio);
    }

    @PostMapping
    public ResponseEntity<LaboratorioResponseDTO> createLaboratorio(@Valid @RequestBody LaboratorioDTO laboratorioDTO) {
        LaboratorioResponseDTO createdLaboratorio = laboratorioService.createLaboratorio(laboratorioDTO);
        return ResponseEntity.ok(createdLaboratorio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaboratorioResponseDTO> updateLaboratorio(@PathVariable Long id,
            @Valid @RequestBody LaboratorioDTO laboratorioDTO) {
        LaboratorioResponseDTO updatedLaboratorio = laboratorioService.updateLaboratorio(id, laboratorioDTO);
        return ResponseEntity.ok(updatedLaboratorio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaboratorio(@PathVariable Long id) {
        laboratorioService.deleteLaboratorio(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/filtros")
    // public ResponseEntity<List<LaboratorioResponseDTO>>
    // getLaboratoriosComFiltros(
    // @ModelAttribute LaboratorioFilterDTO filtros) {
    // List<LaboratorioResponseDTO> laboratorios =
    // laboratorioService.findLaboratoriosComFiltros(filtros);
    // return ResponseEntity.ok(laboratorios);
    // }

    @PostMapping("/filtros")
    public ResponseEntity<List<LaboratorioResponseDTO>> filtrarLaboratorios(
            @Valid @RequestBody LaboratorioFilterDTO filtros) {
        List<LaboratorioResponseDTO> laboratorios = laboratorioService.findLaboratoriosComFiltros(filtros);
        return ResponseEntity.ok(laboratorios);
    }
}