package com.example.demo.dto;

import java.time.LocalDateTime;

public class PessoaDTO {

    private String nome;
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;
    private String observacoes;
    private Long propriedadeId;
    private Long laboratorioId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDateTime dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getPropriedadeId() {
        return propriedadeId;
    }

    public void setPropriedadeId(Long propriedadeId) {
        this.propriedadeId = propriedadeId;
    }

    public Long getLaboratorioId() {
        return laboratorioId;
    }

    public void setLaboratorioId(Long laboratorioId) {
        this.laboratorioId = laboratorioId;
    }
}