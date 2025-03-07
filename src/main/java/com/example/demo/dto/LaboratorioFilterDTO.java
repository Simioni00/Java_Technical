package com.example.demo.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public class LaboratorioFilterDTO {

    private LocalDateTime dataInicialStart;
    private LocalDateTime dataInicialEnd;
    private LocalDateTime dataFinalStart;
    private LocalDateTime dataFinalEnd;
    private String observacoes;

    @NotNull(message = "A quantidade mínima de pessoas é obrigatória")
    private Integer quantidadeMinimaPessoas;

    public LocalDateTime getDataInicialStart() {
        return dataInicialStart;
    }

    public void setDataInicialStart(LocalDateTime dataInicialStart) {
        this.dataInicialStart = dataInicialStart;
    }

    public LocalDateTime getDataInicialEnd() {
        return dataInicialEnd;
    }

    public void setDataInicialEnd(LocalDateTime dataInicialEnd) {
        this.dataInicialEnd = dataInicialEnd;
    }

    public LocalDateTime getDataFinalStart() {
        return dataFinalStart;
    }

    public void setDataFinalStart(LocalDateTime dataFinalStart) {
        this.dataFinalStart = dataFinalStart;
    }

    public LocalDateTime getDataFinalEnd() {
        return dataFinalEnd;
    }

    public void setDataFinalEnd(LocalDateTime dataFinalEnd) {
        this.dataFinalEnd = dataFinalEnd;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getQuantidadeMinimaPessoas() {
        return quantidadeMinimaPessoas;
    }

    public void setQuantidadeMinimaPessoas(Integer quantidadeMinimaPessoas) {
        this.quantidadeMinimaPessoas = quantidadeMinimaPessoas;
    }
}