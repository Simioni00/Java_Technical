package com.example.demo.controller;

import com.example.demo.entity.Propriedade;
import com.example.demo.service.PropriedadeService;
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
    public ResponseEntity<List<Propriedade>> getAllPropriedades() {
        List<Propriedade> propriedades = propriedadeService.getAllPropriedades();
        return ResponseEntity.ok(propriedades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Propriedade> getPropriedadeById(@PathVariable Long id) {
        return propriedadeService.getPropriedadeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Propriedade> createPropriedade(@RequestBody Propriedade propriedade) {
        Propriedade createdPropriedade = propriedadeService.createPropriedade(propriedade);
        return ResponseEntity.ok(createdPropriedade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Propriedade> updatePropriedade(@PathVariable Long id, @RequestBody Propriedade propriedade) {
        Propriedade updatedPropriedade = propriedadeService.updatePropriedade(id, propriedade);
        return updatedPropriedade != null ? ResponseEntity.ok(updatedPropriedade) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropriedade(@PathVariable Long id) {
        propriedadeService.deletePropriedade(id);
        return ResponseEntity.noContent().build();
    }
}