package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class LaboratorioDTO {

    @NotBlank(message = "O nome do laboratório é obrigatório")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}