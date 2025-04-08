package com.challengeitau.challengeitaujunior.service;

import com.challengeitau.challengeitaujunior.dto.ErrorResponse;
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
    private final TransacaoValidator transacaoValidator;

    public TransacaoService(TransactionRepositoryInMemory transactionRepositoryInMemory, TransacaoValidator transacaoValidator) {
        this.transactionRepositoryInMemory = transactionRepositoryInMemory;
        this.transacaoValidator = transacaoValidator;
    }

    public ResponseEntity<?> save(TransacaoDto transacaoDto){
        transacaoValidator.validar(transacaoDto);

        Transaction transaction = new Transaction();
        transaction.setValor(transacaoDto.valor());
        transaction.setDataHora(transacaoDto.dataHora());

        log.info("Salvando transação: {}", transacaoDto);
        transactionRepositoryInMemory.save(transaction);
        log.info("Transação salva com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> deleteAllTransactions(){
        transactionRepositoryInMemory.delete();
        return ResponseEntity.ok().build();
    }

}
