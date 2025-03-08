package com.example.demo.controller;

import com.example.demo.dto.PessoaDTO;
import com.example.demo.entity.Pessoa;
import com.example.demo.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaService pessoaService;

    @Test
    public void testCreatePessoa() throws Exception {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("John Doe");
        pessoaDTO.setDataInicial(LocalDateTime.now());
        pessoaDTO.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoaDTO.setObservacoes("Observação teste");
        pessoaDTO.setPropriedadeId(1L);
        pessoaDTO.setLaboratorioId(1L);

        when(pessoaService.createPessoa(any(PessoaDTO.class))).thenAnswer(invocation -> {
            PessoaDTO dto = invocation.getArgument(0);
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(dto.getNome());
            pessoa.setDataInicial(dto.getDataInicial());
            pessoa.setDataFinal(dto.getDataFinal());
            pessoa.setObservacoes(dto.getObservacoes());
            return pessoa;
        });

        mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(pessoaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("John Doe"))
                .andExpect(jsonPath("$.observacoes").value("Observação teste"));
    }

    @Test
    public void testUpdatePessoaNotFound() throws Exception {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("John Doe");

        when(pessoaService.updatePessoa(1L, pessoaDTO)).thenThrow(new RuntimeException("Pessoa não encontrada"));

        mockMvc.perform(put("/pessoas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(pessoaDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePessoa() throws Exception {
        mockMvc.perform(delete("/pessoas/1"))
                .andExpect(status().isNoContent());
    }
}