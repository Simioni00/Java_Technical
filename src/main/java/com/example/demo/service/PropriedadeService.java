package com.example.demo.service;

import com.example.demo.dto.PropriedadeDTO;
import com.example.demo.dto.PropriedadeResponseDTO;
import com.example.demo.entity.Propriedade;
import com.example.demo.repository.PropriedadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;

    public PropriedadeService(PropriedadeRepository propriedadeRepository) {
        this.propriedadeRepository = propriedadeRepository;
    }

    public List<PropriedadeResponseDTO> getAllPropriedades() {
        return propriedadeRepository.findAll().stream()
                .map(p -> new PropriedadeResponseDTO(p.getId(), p.getNome()))
                .collect(Collectors.toList());
    }

    public PropriedadeResponseDTO getPropriedadeById(Long id) {
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Propriedade não encontrada"));

        return new PropriedadeResponseDTO(propriedade.getId(), propriedade.getNome());
    }

    public PropriedadeResponseDTO createPropriedade(PropriedadeDTO propriedadeDTO) {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome(propriedadeDTO.getNome());

        Propriedade saved = propriedadeRepository.save(propriedade);
        return new PropriedadeResponseDTO(saved.getId(), saved.getNome());
    }

    public PropriedadeResponseDTO updatePropriedade(Long id, PropriedadeDTO propriedadeDTO) {
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Propriedade não encontrada"));

        propriedade.setNome(propriedadeDTO.getNome());
        Propriedade updated = propriedadeRepository.save(propriedade);

        return new PropriedadeResponseDTO(updated.getId(), updated.getNome());
    }

    public void deletePropriedade(Long id) {
        if (!propriedadeRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Propriedade não encontrada");
        }
        propriedadeRepository.deleteById(id);
    }
}