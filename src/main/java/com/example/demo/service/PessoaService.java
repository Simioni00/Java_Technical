package com.example.demo.service;

import com.example.demo.dto.PessoaDTO;
import com.example.demo.entity.Pessoa;
import com.example.demo.entity.Propriedade;
import com.example.demo.entity.Laboratorio;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.repository.PropriedadeRepository;
import com.example.demo.repository.LaboratorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    public Pessoa createPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setDataInicial(pessoaDTO.getDataInicial());
        pessoa.setDataFinal(pessoaDTO.getDataFinal());
        pessoa.setObservacoes(pessoaDTO.getObservacoes());

        Optional<Propriedade> propriedade = propriedadeRepository.findById(pessoaDTO.getPropriedadeId());
        propriedade.ifPresent(pessoa::setPropriedade);

        Optional<Laboratorio> laboratorio = laboratorioRepository.findById(pessoaDTO.getLaboratorioId());
        laboratorio.ifPresent(pessoa::setLaboratorio);

        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> getAllPessoas() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> getPessoaById(Long id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa updatePessoa(Long id, PessoaDTO pessoaDTO) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(id);
        if (optionalPessoa.isPresent()) {
            Pessoa pessoa = optionalPessoa.get();
            pessoa.setNome(pessoaDTO.getNome());
            pessoa.setDataInicial(pessoaDTO.getDataInicial());
            pessoa.setDataFinal(pessoaDTO.getDataFinal());
            pessoa.setObservacoes(pessoaDTO.getObservacoes());

            Optional<Propriedade> propriedade = propriedadeRepository.findById(pessoaDTO.getPropriedadeId());
            propriedade.ifPresent(pessoa::setPropriedade);

            Optional<Laboratorio> laboratorio = laboratorioRepository.findById(pessoaDTO.getLaboratorioId());
            laboratorio.ifPresent(pessoa::setLaboratorio);

            return pessoaRepository.save(pessoa);
        }
        return null;
    }

    public void deletePessoa(Long id) {
        pessoaRepository.deleteById(id);
    }
}