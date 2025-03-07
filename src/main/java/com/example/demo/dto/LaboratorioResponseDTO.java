package com.example.demo.dto;

public class LaboratorioResponseDTO {
    private Long id;
    private String nome;
    private Long quantidadePessoas;

    public LaboratorioResponseDTO(Long id, String nome, Long quantidadePessoas) {
        this.id = id;
        this.nome = nome;
        this.quantidadePessoas = quantidadePessoas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setQuantidadePessoas(Long quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }
}