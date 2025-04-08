package com.challengeitau.challengeitaujunior.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.UnprocessableEntity.class)
    public ResponseEntity<?> handleUnprocessable(HttpClientErrorException.UnprocessableEntity ex) {
        log.warn("Erro 422 Unprocessable Entity: {}", ex.getMessage(), ex);
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleCommonErrors(Exception ex) {
        log.error("Erro de requisição: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpectedErrors(Exception ex) {
        log.error("Erro inesperado capturado: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body("Erro interno no servidor");
    }
}
