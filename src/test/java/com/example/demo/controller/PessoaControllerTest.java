package com.example.demo.controller;

import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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

        @MockitoBean
        private PessoaService pessoaService;

        @Test
        public void testCreatePessoa() throws Exception {
                PessoaResponseDTO responseDTO = new PessoaResponseDTO();
                responseDTO.setId(1L);
                responseDTO.setNome("João");
                responseDTO.setDataInicial(LocalDateTime.now());
                responseDTO.setDataFinal(LocalDateTime.now().plusDays(1));

                when(pessoaService.createPessoa(any())).thenReturn(responseDTO);

                mockMvc.perform(post("/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(responseDTO)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.nome").value("João"));
        }

        @Test
        public void testGetPessoaById() throws Exception {
                PessoaResponseDTO responseDTO = new PessoaResponseDTO();
                responseDTO.setId(1L);
                responseDTO.setNome("João");

                when(pessoaService.getPessoaById(1L)).thenReturn(responseDTO);

                mockMvc.perform(get("/pessoas/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.nome").value("João"));
        }

        @Test
        public void testUpdatePessoa() throws Exception {
                PessoaResponseDTO responseDTO = new PessoaResponseDTO();
                responseDTO.setId(1L);
                responseDTO.setNome("João Atualizado");

                when(pessoaService.updatePessoa(any(Long.class), any())).thenReturn(responseDTO);

                mockMvc.perform(put("/pessoas/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(responseDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1L))
                                .andExpect(jsonPath("$.nome").value("João Atualizado"));
        }

        @Test
        public void testDeletePessoa() throws Exception {
                mockMvc.perform(delete("/pessoas/1"))
                                .andExpect(status().isNoContent());
        }
}