package com.example.demo.validation;

import com.example.demo.dto.PessoaDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DataFinalMaiorOuIgualDataInicialValidator
        implements ConstraintValidator<DataFinalMaiorOuIgualDataInicial, PessoaDTO> {

    @Override
    public boolean isValid(PessoaDTO pessoaDTO, ConstraintValidatorContext context) {
        if (pessoaDTO.getDataInicial() == null || pessoaDTO.getDataFinal() == null) {
            return true;
        }
        return !pessoaDTO.getDataFinal().isBefore(pessoaDTO.getDataInicial());
    }
}