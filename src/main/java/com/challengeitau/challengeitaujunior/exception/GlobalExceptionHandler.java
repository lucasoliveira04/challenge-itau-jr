package com.challengeitau.challengeitaujunior.exception;

import com.challengeitau.challengeitaujunior.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.OffsetDateTime;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TransacaoInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleTransacaoInvalida(TransacaoInvalidaException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Transação inválida",
                ex.getMessage(),
                request.getRequestURI(),
                OffsetDateTime.now()
        );
        log.warn("Transação inválida: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(HttpClientErrorException.UnprocessableEntity.class)
    public ResponseEntity<ErrorResponse> handleUnprocessable(HttpClientErrorException.UnprocessableEntity ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Unprocessable Entity",
                ex.getMessage(),
                request.getRequestURI(),
                OffsetDateTime.now()
        );

        log.warn("Erro 422 Unprocessable Entity: {}", ex.getMessage(), ex);
        return ResponseEntity.unprocessableEntity().body(error);
    }

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleCommonErrors(Exception ex, HttpServletRequest request) {

        Map<Class<? extends Exception>, HttpStatus> exceptionStatusMap = Map.of(
                NullPointerException.class, HttpStatus.INTERNAL_SERVER_ERROR,
                IllegalArgumentException.class, HttpStatus.BAD_REQUEST
        );

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorName = "";

        for (Map.Entry<Class<? extends Exception>, HttpStatus> entry: exceptionStatusMap.entrySet()){
            if (entry.getKey().isInstance(ex)){
                status = entry.getValue();
                errorName = entry.getKey().getSimpleName();
                break;
            }
        }

        ErrorResponse error = new ErrorResponse(
                status.value(),
                errorName,
                ex.getMessage(),
                request.getRequestURI(),
                OffsetDateTime.now()
        );

        log.error("Erro de requisição: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpectedErrors(Exception ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Exception Entity",
                ex.getMessage(),
                request.getRequestURI(),
                OffsetDateTime.now()
        );

        log.error("Erro inesperado capturado: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(error);
    }
}
