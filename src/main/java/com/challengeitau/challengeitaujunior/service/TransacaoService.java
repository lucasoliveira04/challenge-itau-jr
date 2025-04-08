package com.challengeitau.challengeitaujunior.service;

import com.challengeitau.challengeitaujunior.dto.TransacaoDto;
import com.challengeitau.challengeitaujunior.exception.TransacaoInvalidaException;
import com.challengeitau.challengeitaujunior.modal.Transaction;
import com.challengeitau.challengeitaujunior.modal.TransactionRepositoryInMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j
@Service
public class TransacaoService {
    private final TransactionRepositoryInMemory transactionRepositoryInMemory;

    public TransacaoService(TransactionRepositoryInMemory transactionRepositoryInMemory) {
        this.transactionRepositoryInMemory = transactionRepositoryInMemory;
    }

    public ResponseEntity<?> save(TransacaoDto transacaoDto){
        validarTransacao(transacaoDto);

        Transaction transaction = new Transaction();
        transaction.setValor(transacaoDto.valor());
        transaction.setDataHora(transacaoDto.dataHora());

        log.info("Salvando transação: {}", transacaoDto);
        transactionRepositoryInMemory.save(transaction);
        log.info("Transação salva com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void validarTransacao(TransacaoDto transacaoDto){
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

    public ResponseEntity<?> deleteAllTransactions(){
        transactionRepositoryInMemory.delete();
        return ResponseEntity.ok().build();
    }

}
