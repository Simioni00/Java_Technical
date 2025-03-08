package com.example.demo.controller;

import com.example.demo.dto.PropriedadeDTO;
import com.example.demo.dto.PropriedadeResponseDTO;
import com.example.demo.entity.Propriedade;
import com.example.demo.service.PropriedadeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropriedadeController.class)
public class PropriedadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropriedadeService propriedadeService;

    @Test
    public void testCreatePropriedade() throws Exception {
        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setNome("Propriedade 1");

        PropriedadeResponseDTO responseDTO = new PropriedadeResponseDTO(1L, "Propriedade 1");

        when(propriedadeService.createPropriedade(any(PropriedadeDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/propriedades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(propriedadeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Propriedade 1"));
    }

    @Test
    public void testUpdatePropriedadeSuccess() throws Exception {
        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setNome("Propriedade Atualizada");

        PropriedadeResponseDTO responseDTO = new PropriedadeResponseDTO(1L, "Propriedade Atualizada");

        when(propriedadeService.updatePropriedade(any(Long.class), any(PropriedadeDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/propriedades/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(propriedadeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Propriedade Atualizada"));
    }

    @Test
    public void testDeletePropriedade() throws Exception {
        mockMvc.perform(delete("/propriedades/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreatePropriedadeWithInvalidData() throws Exception {
        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
        propriedadeDTO.setNome("");

        mockMvc.perform(post("/propriedades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(propriedadeDTO)))
                .andExpect(status().isBadRequest());
    }
}