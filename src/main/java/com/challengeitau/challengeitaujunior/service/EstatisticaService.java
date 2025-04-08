package com.challengeitau.challengeitaujunior.service;

import com.challengeitau.challengeitaujunior.dto.EstatisticaDto;
import com.challengeitau.challengeitaujunior.modal.Transaction;
import com.challengeitau.challengeitaujunior.modal.TransactionRepositoryInMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@Slf4j
public class EstatisticaService {
    private final TransactionRepositoryInMemory transactionRepositoryInMemory;

    public EstatisticaService(TransactionRepositoryInMemory transactionRepositoryInMemory) {
        this.transactionRepositoryInMemory = transactionRepositoryInMemory;
    }

    public ResponseEntity<?> getEstatistica(int time) {
        log.info("Calculando estatísticas das transações dos últimos 60 segundos");

        long inicio = System.nanoTime();

        List<Transaction> transacoesRecentes = transactionRepositoryInMemory.getTransactions().stream()
                .filter(t -> t.getDataHora().isAfter(OffsetDateTime.now().minusSeconds(time)))
                .toList();

        int count = transacoesRecentes.size();
        log.debug("Transações recentes encontradas: {}", count);

        double sum = round(transacoesRecentes.stream()
                .mapToDouble(Transaction::getValor)
                .sum());
        log.debug("Soma dos valores: {}", sum);

        double avg = round(count > 0 ? sum / count : 0.0);
        double min = round(transacoesRecentes.stream()
                .mapToDouble(Transaction::getValor)
                .min()
                .orElse(0.0));
        double max = round(transacoesRecentes.stream()
                .mapToDouble(Transaction::getValor)
                .max()
                .orElse(0.0));

        EstatisticaDto estatisticaDto = new EstatisticaDto(count, sum, avg, min, max);

        long fim = System.nanoTime();
        long duracaoEmMicros = (fim - inicio) / 1_000;
        System.out.println("Tempo para calcular estatísticas: " + duracaoEmMicros + " μs");

        log.info("Estatísticas calculadas: {}", estatisticaDto);
        return ResponseEntity.ok().body(estatisticaDto);
    }

    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
