package com.example.demo.dto;

public class PropriedadeResponseDTO {

    private Long id;
    private String nome;

    public PropriedadeResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}