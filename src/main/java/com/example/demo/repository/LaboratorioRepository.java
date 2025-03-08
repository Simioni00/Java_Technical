package com.example.demo.repository;

import com.example.demo.dto.LaboratorioResponseDTO;
import com.example.demo.entity.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {

        @Query("SELECT new com.example.demo.dto.LaboratorioResponseDTO(l.id, UPPER(l.nome), COUNT(p.id)) " +
                        "FROM Laboratorio l " +
                        "JOIN l.pessoas p " +
                        "WHERE (:dataInicialStart IS NULL OR p.dataInicial >= :dataInicialStart) " +
                        "AND (:dataInicialEnd IS NULL OR p.dataInicial <= :dataInicialEnd) " +
                        "AND (:dataFinalStart IS NULL OR p.dataFinal >= :dataFinalStart) " +
                        "AND (:dataFinalEnd IS NULL OR p.dataFinal <= :dataFinalEnd) " +
                        "AND (:observacoes IS NULL OR p.observacoes LIKE %:observacoes%) " +
                        "GROUP BY l.id, l.nome " +
                        "HAVING COUNT(p.id) >= :quantidadeMinimaPessoas " +
                        "ORDER BY COUNT(p.id) DESC, " +
                        "MIN(p.dataInicial) ASC")
        List<LaboratorioResponseDTO> findLaboratoriosComFiltros(
                        @Param("dataInicialStart") LocalDateTime dataInicialStart,
                        @Param("dataInicialEnd") LocalDateTime dataInicialEnd,
                        @Param("dataFinalStart") LocalDateTime dataFinalStart,
                        @Param("dataFinalEnd") LocalDateTime dataFinalEnd,
                        @Param("observacoes") String observacoes,
                        @Param("quantidadeMinimaPessoas") Integer quantidadeMinimaPessoas);
}