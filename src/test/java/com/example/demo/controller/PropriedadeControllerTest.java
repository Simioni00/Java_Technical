package com.example.demo.controller;

import com.example.demo.dto.PropriedadeResponseDTO;
import com.example.demo.service.PropriedadeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropriedadeController.class)
public class PropriedadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PropriedadeService propriedadeService;

    @Test
    public void testGetAllPropriedades() throws Exception {
        mockMvc.perform(get("/propriedades"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPropriedadeById() throws Exception {
        PropriedadeResponseDTO responseDTO = new PropriedadeResponseDTO(1L, "Fazenda Feliz");

        when(propriedadeService.getPropriedadeById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/propriedades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Fazenda Feliz"));
    }

    @Test
    public void testCreatePropriedade() throws Exception {
        PropriedadeResponseDTO responseDTO = new PropriedadeResponseDTO(1L, "Fazenda Feliz");

        when(propriedadeService.createPropriedade(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/propriedades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(responseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Fazenda Feliz"));
    }

    @Test
    public void testUpdatePropriedade() throws Exception {
        PropriedadeResponseDTO responseDTO = new PropriedadeResponseDTO(1L, "Fazenda Feliz Atualizada");

        when(propriedadeService.updatePropriedade(any(Long.class), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/propriedades/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(responseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Fazenda Feliz Atualizada"));
    }

    @Test
    public void testDeletePropriedade() throws Exception {
        mockMvc.perform(delete("/propriedades/1"))
                .andExpect(status().isNoContent());
    }
}