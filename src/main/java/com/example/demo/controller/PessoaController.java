package com.example.demo.controller;

import com.example.demo.dto.PessoaProjectionDTO;
import com.example.demo.dto.PessoaRequestDTO;
import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.service.PessoaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> createPessoa(@Valid @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        PessoaResponseDTO responseDTO = pessoaService.createPessoa(pessoaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PessoaProjectionDTO>> getAllPessoas() {
        List<PessoaProjectionDTO> pessoas = pessoaService.getAllPessoasProjected();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> getPessoaById(@PathVariable Long id) {
        PessoaResponseDTO responseDTO = pessoaService.getPessoaById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(@PathVariable Long id,
            @Valid @RequestBody PessoaRequestDTO pessoaRequestDTO) {
        PessoaResponseDTO responseDTO = pessoaService.updatePessoa(id, pessoaRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }
}