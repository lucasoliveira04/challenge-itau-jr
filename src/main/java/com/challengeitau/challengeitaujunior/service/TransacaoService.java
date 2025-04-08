package com.challengeitau.challengeitaujunior.service;

import com.challengeitau.challengeitaujunior.dto.TransacaoDto;
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
        Transaction transaction = new Transaction();

        transaction.setValor(transacaoDto.valor());
        transaction.setDataHora(transacaoDto.dataHora());

        if (transacaoDto.valor() < 0
                || transacaoDto.dataHora().isAfter(OffsetDateTime.now())
                || !hasTwoOrFewerDecimalPlaces(transacaoDto.valor())){
            return ResponseEntity.unprocessableEntity().build();
        }

        log.info("Salvando transação: {}", transacaoDto);
        transactionRepositoryInMemory.save(transaction);
        log.info("Transação salva com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
