package com.example.demo.repository;

import com.example.demo.dto.PessoaProjectionDTO;
import com.example.demo.entity.Pessoa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT new com.example.demo.dto.PessoaProjectionDTO(" +
            "p.id, p.nome, p.dataInicial, p.dataFinal, p.observacoes, p.propriedade.id, p.laboratorio.id) " +
            "FROM Pessoa p")
    List<PessoaProjectionDTO> findAllProjected();
}