package com.example.demo.controller;

import com.example.demo.dto.PropriedadeDTO;
import com.example.demo.dto.PropriedadeResponseDTO;
import com.example.demo.service.PropriedadeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/propriedades")
public class PropriedadeController {

    private final PropriedadeService propriedadeService;

    public PropriedadeController(PropriedadeService propriedadeService) {
        this.propriedadeService = propriedadeService;
    }

    @GetMapping
    public ResponseEntity<List<PropriedadeResponseDTO>> getAllPropriedades() {
        return ResponseEntity.ok(propriedadeService.getAllPropriedades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropriedadeResponseDTO> getPropriedadeById(@PathVariable Long id) {
        return ResponseEntity.ok(propriedadeService.getPropriedadeById(id));
    }

    @PostMapping
    public ResponseEntity<PropriedadeResponseDTO> createPropriedade(@RequestBody @Valid PropriedadeDTO propriedadeDTO) {
        return ResponseEntity.ok(propriedadeService.createPropriedade(propriedadeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropriedadeResponseDTO> updatePropriedade(@PathVariable Long id,
            @RequestBody @Valid PropriedadeDTO propriedadeDTO) {
        return ResponseEntity.ok(propriedadeService.updatePropriedade(id, propriedadeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropriedade(@PathVariable Long id) {
        propriedadeService.deletePropriedade(id);
        return ResponseEntity.noContent().build();
    }
}