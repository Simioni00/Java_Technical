package com.example.demo.controller;

import com.example.demo.dto.LaboratorioDTO;
import com.example.demo.dto.LaboratorioResponseDTO;
import com.example.demo.service.LaboratorioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(LaboratorioController.class)
public class LaboratorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LaboratorioService laboratorioService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

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

    @Test
    public void testCreateLaboratorioWithInvalidData() throws Exception {
        LaboratorioDTO invalidLaboratorioDTO = new LaboratorioDTO();
        invalidLaboratorioDTO.setNome("");

        mockMvc.perform(post("/laboratorios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidLaboratorioDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateLaboratorioWithInvalidData() throws Exception {
        LaboratorioDTO invalidLaboratorioDTO = new LaboratorioDTO();
        invalidLaboratorioDTO.setNome("");

        mockMvc.perform(put("/laboratorios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidLaboratorioDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllLaboratorios() throws Exception {
        LaboratorioResponseDTO responseDTO1 = new LaboratorioResponseDTO(1L, "Laboratório 1", 0L);
        LaboratorioResponseDTO responseDTO2 = new LaboratorioResponseDTO(2L, "Laboratório 2", 0L);

        when(laboratorioService.getAllLaboratorios()).thenReturn(List.of(responseDTO1, responseDTO2));

        mockMvc.perform(get("/laboratorios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Laboratório 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nome").value("Laboratório 2"));
    }
}