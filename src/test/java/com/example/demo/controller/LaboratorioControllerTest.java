package com.example.demo.controller;

import com.example.demo.dto.LaboratorioResponseDTO;
import com.example.demo.service.LaboratorioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LaboratorioController.class)
public class LaboratorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LaboratorioService laboratorioService;

    @Test
    public void testGetLaboratorioById() throws Exception {
        LaboratorioResponseDTO responseDTO = new LaboratorioResponseDTO(1L, "Laboratório 1", 0L);

        when(laboratorioService.getLaboratorioById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/laboratorios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Laboratório 1"));
    }

    @Test
    public void testGetLaboratorioByIdNotFound() throws Exception {
        when(laboratorioService.getLaboratorioById(1L))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/laboratorios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateLaboratorio() throws Exception {
        LaboratorioResponseDTO responseDTO = new LaboratorioResponseDTO(1L, "Laboratório 1", 0L);

        when(laboratorioService.createLaboratorio(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/laboratorios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(responseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Laboratório 1"));
    }

    @Test
    public void testUpdateLaboratorio() throws Exception {
        LaboratorioResponseDTO responseDTO = new LaboratorioResponseDTO(1L, "Laboratório Atualizado", 0L);

        when(laboratorioService.updateLaboratorio(any(Long.class), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/laboratorios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(responseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Laboratório Atualizado"));
    }

    @Test
    public void testDeleteLaboratorio() throws Exception {
        mockMvc.perform(delete("/laboratorios/1"))
                .andExpect(status().isNoContent());
    }
}