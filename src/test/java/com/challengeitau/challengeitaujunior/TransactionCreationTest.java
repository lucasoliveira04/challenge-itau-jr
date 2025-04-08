package com.challengeitau.challengeitaujunior;

import com.challengeitau.challengeitaujunior.dto.TransacaoDto;
import com.challengeitau.challengeitaujunior.modal.TransactionRepositoryInMemory;
import com.challengeitau.challengeitaujunior.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionCreationTest {

    private TransacaoService transacaoService;
    private TransactionRepositoryInMemory transactionRepositoryInMemory;

    @BeforeEach
    void setup() {
        transactionRepositoryInMemory = new TransactionRepositoryInMemory();
        transacaoService = new TransacaoService(transactionRepositoryInMemory);
    }

    @Test
    void shouldReturn422WhenValueIsNegative() {
        TransacaoDto transacaoDto = new TransacaoDto(-10.0, OffsetDateTime.now());
        ResponseEntity<?> response = transacaoService.save(transacaoDto);

        assertEquals(422, response.getStatusCodeValue());
    }

    @Test
    void shoudReturn422WhenDataHoraIsFuture(){
        OffsetDateTime future = OffsetDateTime.now().plusDays(1);

        TransacaoDto transacaoDto = new TransacaoDto(100.0, future);
        ResponseEntity<?> response = transacaoService.save(transacaoDto);

        assertEquals(422, response.getStatusCodeValue());
    }

    @Test
    void shouldReturn422WhenValueHasMoreThanTwoDecimalPlaces() {
        TransacaoDto transacaoDto = new TransacaoDto(10.123, OffsetDateTime.now());
        ResponseEntity<?> response = transacaoService.save(transacaoDto);

        assertEquals(422, response.getStatusCodeValue());
    }

    @Test
    void shouldReturn201WhenTransactionIsValid() {
        TransacaoDto transacaoDto = new TransacaoDto(100.0, OffsetDateTime.now());
        ResponseEntity<?> response = transacaoService.save(transacaoDto);

        assertEquals(201, response.getStatusCodeValue());
    }

}
