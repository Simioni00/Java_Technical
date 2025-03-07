package com.example.demo.service;

import com.example.demo.dto.PessoaDTO;
import com.example.demo.entity.Pessoa;
import com.example.demo.entity.Propriedade;
import com.example.demo.entity.Laboratorio;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.repository.PropriedadeRepository;
import com.example.demo.repository.LaboratorioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PropriedadeRepository propriedadeRepository;
    private final LaboratorioRepository laboratorioRepository;

    public PessoaService(PessoaRepository pessoaRepository, PropriedadeRepository propriedadeRepository,
            LaboratorioRepository laboratorioRepository) {
        this.pessoaRepository = pessoaRepository;
        this.propriedadeRepository = propriedadeRepository;
        this.laboratorioRepository = laboratorioRepository;
    }

    public Pessoa createPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDataInicial(pessoaDTO.getDataInicial());
        pessoa.setDataFinal(pessoaDTO.getDataFinal());
        pessoa.setObservacoes(pessoaDTO.getObservacoes());

        Propriedade propriedade = propriedadeRepository.findById(pessoaDTO.getPropriedadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Propriedade não encontrada"));

        Laboratorio laboratorio = laboratorioRepository.findById(pessoaDTO.getLaboratorioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Laboratório não encontrado"));

        pessoa.setPropriedade(propriedade);
        pessoa.setLaboratorio(laboratorio);

        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> getAllPessoas() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> getPessoaById(Long id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa updatePessoa(Long id, PessoaDTO pessoaDTO) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    pessoa.setNome(pessoaDTO.getNome());
                    pessoa.setDataInicial(pessoaDTO.getDataInicial());
                    pessoa.setDataFinal(pessoaDTO.getDataFinal());
                    pessoa.setObservacoes(pessoaDTO.getObservacoes());

                    Propriedade propriedade = propriedadeRepository.findById(pessoaDTO.getPropriedadeId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Propriedade não encontrada"));

                    Laboratorio laboratorio = laboratorioRepository.findById(pessoaDTO.getLaboratorioId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Laboratório não encontrado"));

                    pessoa.setPropriedade(propriedade);
                    pessoa.setLaboratorio(laboratorio);

                    return pessoaRepository.save(pessoa);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
    }

    public void deletePessoa(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada");
        }
        pessoaRepository.deleteById(id);
    }
}