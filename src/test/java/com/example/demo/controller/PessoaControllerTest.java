package com.example.demo.controller;

import com.example.demo.dto.PessoaRequestDTO;
import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaService pessoaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreatePessoa() throws Exception {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setNome("John Doe");
        pessoaRequestDTO.setDataInicial(LocalDateTime.now());
        pessoaRequestDTO.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoaRequestDTO.setObservacoes("Observação teste");

        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
        pessoaResponseDTO.setNome("John Doe");
        pessoaResponseDTO.setObservacoes("Observação teste");

        when(pessoaService.createPessoa(any(PessoaRequestDTO.class))).thenReturn(pessoaResponseDTO);

        mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("John Doe"))
                .andExpect(jsonPath("$.observacoes").value("Observação teste"));
    }

    @Test
    public void testGetPessoaById() throws Exception {
        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
        pessoaResponseDTO.setId(1L);
        pessoaResponseDTO.setNome("John Doe");
        pessoaResponseDTO.setObservacoes("Observação teste");

        when(pessoaService.getPessoaById(1L)).thenReturn(pessoaResponseDTO);

        mockMvc.perform(get("/pessoas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("John Doe"))
                .andExpect(jsonPath("$.observacoes").value("Observação teste"));
    }

    @Test
    public void testUpdatePessoa() throws Exception {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setNome("John Doe");
        pessoaRequestDTO.setDataInicial(LocalDateTime.now());
        pessoaRequestDTO.setDataFinal(LocalDateTime.now().plusDays(1));
        pessoaRequestDTO.setObservacoes("Observação atualizada");

        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
        pessoaResponseDTO.setId(1L);
        pessoaResponseDTO.setNome("John Doe");
        pessoaResponseDTO.setObservacoes("Observação atualizada");

        when(pessoaService.updatePessoa(1L, pessoaRequestDTO)).thenReturn(pessoaResponseDTO);

        mockMvc.perform(put("/pessoas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("John Doe"))
                .andExpect(jsonPath("$.observacoes").value("Observação atualizada"));
    }

    @Test
    public void testUpdatePessoaNotFound() throws Exception {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO();
        pessoaRequestDTO.setNome("John Doe");

        when(pessoaService.updatePessoa(1L, pessoaRequestDTO))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));

        mockMvc.perform(put("/pessoas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaRequestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePessoa() throws Exception {
        mockMvc.perform(delete("/pessoas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeletePessoaNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"))
                .when(pessoaService).deletePessoa(1L);

        mockMvc.perform(delete("/pessoas/1"))
                .andExpect(status().isNotFound());
    }
}