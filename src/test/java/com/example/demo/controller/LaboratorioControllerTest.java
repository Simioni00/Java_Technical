package com.example.demo.controller;

import com.example.demo.entity.Laboratorio;
import com.example.demo.service.LaboratorioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LaboratorioController.class)
public class LaboratorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LaboratorioService laboratorioService;

    @Test
    public void testGetLaboratorioById() throws Exception {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(1L);
        laboratorio.setNome("Laboratório 1");

        when(laboratorioService.getLaboratorioById(1L)).thenReturn(Optional.of(laboratorio));

        mockMvc.perform(get("/laboratorios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Laboratório 1"));
    }

    @Test
    public void testGetLaboratorioByIdNotFound() throws Exception {
        when(laboratorioService.getLaboratorioById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/laboratorios/1"))
                .andExpect(status().isNotFound());
    }
}