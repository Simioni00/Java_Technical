package com.example.demo.controller;

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
        Propriedade propriedade = new Propriedade();
        propriedade.setId(1L);
        propriedade.setNome("Propriedade 1");

        when(propriedadeService.createPropriedade(any(Propriedade.class))).thenReturn(propriedade);

        mockMvc.perform(post("/propriedades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(propriedade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdatePropriedadeNotFound() throws Exception {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome("Propriedade 1");

        when(propriedadeService.updatePropriedade(1L, propriedade))
                .thenThrow(new RuntimeException("Propriedade não encontrada"));

        mockMvc.perform(put("/propriedades/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(propriedade)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePropriedade() throws Exception {
        mockMvc.perform(delete("/propriedades/1"))
                .andExpect(status().isNoContent());
    }
}