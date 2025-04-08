package com.challengeitau.challengeitaujunior.controller;

import com.challengeitau.challengeitaujunior.dto.TransacaoDto;
import com.challengeitau.challengeitaujunior.service.TransacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@Slf4j
public class TransactionController {
    private final TransacaoService transacaoService;

    public TransactionController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<?> createdTransaction(@RequestBody TransacaoDto transacaoDto){
        log.info("Recebida requisição POST /transacao com body: {}", transacaoDto);
        ResponseEntity<?> response = transacaoService.save(transacaoDto);
        log.info("Resposta da criação de transação: status {}", response.getStatusCode());
        return response;
    }

    @DeleteMapping
    public ResponseEntity<?> deleted(){
        log.info("Recebida requisição DELETE /transacao");
        ResponseEntity<?> response = transacaoService.deleteAllTransactions();
        log.info("Resposta da exclusão de transações: status {}", response.getStatusCode());
        return response;
    }
}
