package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class PropriedadeDTO {

    @NotBlank(message = "O nome da propriedade é obrigatório")
    private String nome;

    private Long id;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}