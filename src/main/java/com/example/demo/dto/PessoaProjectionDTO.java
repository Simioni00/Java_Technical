package com.example.demo.dto;

import java.time.LocalDateTime;

public class PessoaProjectionDTO {
    private Long id;
    private String nome;
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;
    private String observacoes;
    private Long propriedadeId;
    private Long laboratorioId;

    public PessoaProjectionDTO(Long id, String nome, LocalDateTime dataInicial, LocalDateTime dataFinal, String observacoes, Long propriedadeId, Long laboratorioId) {
        this.id = id;
        this.nome = nome;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.observacoes = observacoes;
        this.propriedadeId = propriedadeId;
        this.laboratorioId = laboratorioId;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDateTime getDataInicial() {
        return dataInicial;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Long getPropriedadeId() {
        return propriedadeId;
    }

    public Long getLaboratorioId() {
        return laboratorioId;
    }
}