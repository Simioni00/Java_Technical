package com.example.demo.service;

import com.example.demo.entity.Propriedade;
import com.example.demo.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

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
        Optional<Propriedade> optionalPropriedade = propriedadeRepository.findById(id);
        if (optionalPropriedade.isPresent()) {
            Propriedade propriedade = optionalPropriedade.get();
            propriedade.setNome(propriedadeDetails.getNome());
            return propriedadeRepository.save(propriedade);
        }
        return null;
    }

    public void deletePropriedade(Long id) {
        propriedadeRepository.deleteById(id);
    }
}