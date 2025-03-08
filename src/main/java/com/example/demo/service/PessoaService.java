package com.example.demo.service;

import com.example.demo.dto.LaboratorioDTO;
import com.example.demo.dto.PessoaDTO;
import com.example.demo.dto.PessoaProjectionDTO;
import com.example.demo.dto.PessoaRequestDTO;
import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.dto.PropriedadeDTO;
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

    public PessoaResponseDTO createPessoa(PessoaRequestDTO pessoaRequestDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaRequestDTO.getNome());
        pessoa.setDataInicial(pessoaRequestDTO.getDataInicial());
        pessoa.setDataFinal(pessoaRequestDTO.getDataFinal());
        pessoa.setObservacoes(pessoaRequestDTO.getObservacoes());

        Propriedade propriedade = propriedadeRepository.findById(pessoaRequestDTO.getPropriedadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Propriedade não encontrada"));

        Laboratorio laboratorio = laboratorioRepository.findById(pessoaRequestDTO.getLaboratorioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Laboratório não encontrado"));

        pessoa.setPropriedade(propriedade);
        pessoa.setLaboratorio(laboratorio);

        Pessoa savedPessoa = pessoaRepository.save(pessoa);
        return convertToPessoaResponseDTO(savedPessoa);
    }

    public List<PessoaProjectionDTO> getAllPessoasProjected() {
        return pessoaRepository.findAllProjected();
    }

    public PessoaResponseDTO getPessoaById(Long id) {
        return pessoaRepository.findById(id)
                .map(this::convertToPessoaResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
    }

    public PessoaResponseDTO updatePessoa(Long id, PessoaRequestDTO pessoaRequestDTO) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    pessoa.setNome(pessoaRequestDTO.getNome());
                    pessoa.setDataInicial(pessoaRequestDTO.getDataInicial());
                    pessoa.setDataFinal(pessoaRequestDTO.getDataFinal());
                    pessoa.setObservacoes(pessoaRequestDTO.getObservacoes());

                    Propriedade propriedade = propriedadeRepository
                            .findById(pessoaRequestDTO.getPropriedadeId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Propriedade não encontrada"));

                    Laboratorio laboratorio = laboratorioRepository
                            .findById(pessoaRequestDTO.getLaboratorioId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Laboratório não encontrado"));

                    pessoa.setPropriedade(propriedade);
                    pessoa.setLaboratorio(laboratorio);

                    Pessoa updatedPessoa = pessoaRepository.save(pessoa);
                    return convertToPessoaResponseDTO(updatedPessoa);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
    }

    public void deletePessoa(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada");
        }
        pessoaRepository.deleteById(id);
    }

    private PessoaResponseDTO convertToPessoaResponseDTO(Pessoa pessoa) {
        PessoaResponseDTO responseDTO = new PessoaResponseDTO();
        responseDTO.setId(pessoa.getId());
        responseDTO.setNome(pessoa.getNome());
        responseDTO.setDataInicial(pessoa.getDataInicial());
        responseDTO.setDataFinal(pessoa.getDataFinal());
        responseDTO.setObservacoes(pessoa.getObservacoes());

        PropriedadeDTO propriedadeDTO = new PropriedadeDTO();
    //    propriedadeDTO.setId(pessoa.getPropriedade().getId());
        propriedadeDTO.setNome(pessoa.getPropriedade().getNome());
        responseDTO.setInfosPropriedade(propriedadeDTO);

        LaboratorioDTO laboratorioDTO = new LaboratorioDTO();
        laboratorioDTO.setId(pessoa.getLaboratorio().getId());
        laboratorioDTO.setNome(pessoa.getLaboratorio().getNome());
        responseDTO.setLaboratorio(laboratorioDTO);

        return responseDTO;
    }
}