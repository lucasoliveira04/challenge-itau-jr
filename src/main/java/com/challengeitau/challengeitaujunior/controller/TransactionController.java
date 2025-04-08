package com.challengeitau.challengeitaujunior.controller;

import com.challengeitau.challengeitaujunior.dto.TransacaoDto;
import com.challengeitau.challengeitaujunior.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transações", description = "Endpoint para gerenciamento de transações")
@RestController
@RequestMapping("/transacao")
@Slf4j
public class TransactionController {
    private final TransacaoService transacaoService;

    public TransactionController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova transação")
    @ApiResponse(responseCode = "201", description = "Transação criada com sucesso")
    @ApiResponse(responseCode = "422", description = "Transação inválida")
    @ApiResponse(responseCode = "400", description = "Erro no corpo da requisição.")
    public ResponseEntity<?> createdTransaction(@RequestBody TransacaoDto transacaoDto){
        log.info("Recebida requisição POST /transacao com body: {}", transacaoDto);
        ResponseEntity<?> response = transacaoService.save(transacaoDto);
        log.info("Resposta da criação de transação: status {}", response.getStatusCode());
        return response;
    }

    @DeleteMapping
    @Operation(summary = "Remove todas as transações")
    @ApiResponse(responseCode = "200", description = "Transações removidas com sucesso")
    public ResponseEntity<?> deleted(){
        log.info("Recebida requisição DELETE /transacao");
        ResponseEntity<?> response = transacaoService.deleteAllTransactions();
        log.info("Resposta da exclusão de transações: status {}", response.getStatusCode());
        return response;
    }
}
