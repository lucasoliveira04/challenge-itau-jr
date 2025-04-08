package com.challengeitau.challengeitaujunior.service;

import com.challengeitau.challengeitaujunior.dto.TransacaoDto;
import com.challengeitau.challengeitaujunior.exception.TransacaoInvalidaException;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class TransacaoValidator {
    public void validar(TransacaoDto transacaoDto) {
        if (transacaoDto.valor() < 0) {
            throw new TransacaoInvalidaException("O valor da transação não pode ser negativo");
        }
        if (transacaoDto.dataHora().isAfter(OffsetDateTime.now())) {
            throw new TransacaoInvalidaException("A data da transação não pode ser futura");
        }
        if (!hasTwoOrFewerDecimalPlaces(transacaoDto.valor())) {
            throw new TransacaoInvalidaException("O valor da transação deve ter no máximo duas casas decimais");
        }
    }

    private boolean hasTwoOrFewerDecimalPlaces(Double valor){
        String[] parts = String.valueOf(valor).split("\\.");
        return parts.length < 2 || parts[1].length() <= 2;
    }
}
