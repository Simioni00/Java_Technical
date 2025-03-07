package com.example.demo.service;

import com.example.demo.entity.Propriedade;
import com.example.demo.repository.PropriedadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;

    public PropriedadeService(PropriedadeRepository propriedadeRepository) {
        this.propriedadeRepository = propriedadeRepository;
    }

    public List<Propriedade> getAllPropriedades() {
        return propriedadeRepository.findAll();
    }

    public Optional<Propriedade> getPropriedadeById(Long id) {
        return propriedadeRepository.findById(id);
    }

    public Propriedade createPropriedade(Propriedade propriedade) {
        return propriedadeRepository.save(propriedade);
    }

    public Propriedade updatePropriedade(Long id, Propriedade propriedadeDetails) {
        return propriedadeRepository.findById(id)
                .map(propriedade -> {
                    propriedade.setNome(propriedadeDetails.getNome());
                    return propriedadeRepository.save(propriedade);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Propriedade não encontrada"));
    }

    public void deletePropriedade(Long id) {
        if (!propriedadeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Propriedade não encontrada");
        }
        propriedadeRepository.deleteById(id);
    }
}