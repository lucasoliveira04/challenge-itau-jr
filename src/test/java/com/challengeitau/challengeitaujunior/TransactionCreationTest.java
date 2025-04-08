package com.challengeitau.challengeitaujunior;

import com.challengeitau.challengeitaujunior.dto.TransacaoDto;
import com.challengeitau.challengeitaujunior.exception.TransacaoInvalidaException;
import com.challengeitau.challengeitaujunior.modal.TransactionRepositoryInMemory;
import com.challengeitau.challengeitaujunior.service.TransacaoService;
import com.challengeitau.challengeitaujunior.service.TransacaoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionCreationTest {

    private TransacaoService transacaoService;
    private TransactionRepositoryInMemory transactionRepositoryInMemory;
    private TransacaoValidator transacaoValidator;

    @BeforeEach
    void setup() {
        transactionRepositoryInMemory = new TransactionRepositoryInMemory();
        transacaoValidator = new TransacaoValidator();
        transacaoService = new TransacaoService(transactionRepositoryInMemory, transacaoValidator);
    }

    @Test
    void shouldThrowExceptionWhenValueIsNegative() {
        TransacaoDto transacaoDto = new TransacaoDto(-10.0, OffsetDateTime.now());

        assertThrows(TransacaoInvalidaException.class, () -> {
            transacaoService.save(transacaoDto);
        });
    }

    @Test
    void shouldThrowExceptionWhenDataHoraIsFuture() {
        OffsetDateTime future = OffsetDateTime.now().plusDays(1);
        TransacaoDto transacaoDto = new TransacaoDto(100.0, future);

        assertThrows(TransacaoInvalidaException.class, () -> {
            transacaoService.save(transacaoDto);
        });
    }

    @Test
    void shouldThrowExceptionWhenValueHasMoreThanTwoDecimalPlaces() {
        TransacaoDto transacaoDto = new TransacaoDto(10.123, OffsetDateTime.now());

        assertThrows(TransacaoInvalidaException.class, () -> {
            transacaoService.save(transacaoDto);
        });
    }

    @Test
    void shouldReturn201WhenTransactionIsValid() {
        TransacaoDto transacaoDto = new TransacaoDto(100.0, OffsetDateTime.now());
        ResponseEntity<?> response = transacaoService.save(transacaoDto);

        assertEquals(201, response.getStatusCodeValue());
    }

}
