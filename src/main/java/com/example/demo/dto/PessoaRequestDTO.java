package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PessoaRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A data inicial é obrigatória")
    private LocalDateTime dataInicial;

    @NotNull(message = "A data final é obrigatória")
    private LocalDateTime dataFinal;

    private String observacoes;

    @NotNull(message = "As informações da propriedade são obrigatórias")
    private PropriedadeDTO infosPropriedade;

    @NotNull(message = "As informações do laboratório são obrigatórias")
    private LaboratorioDTO laboratorio;

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

    public PropriedadeDTO getInfosPropriedade() {
        return infosPropriedade;
    }

    public void setInfosPropriedade(PropriedadeDTO infosPropriedade) {
        this.infosPropriedade = infosPropriedade;
    }

    public LaboratorioDTO getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(LaboratorioDTO laboratorio) {
        this.laboratorio = laboratorio;
    }
}