package com.challengeitau.challengeitaujunior.controller;

import com.challengeitau.challengeitaujunior.service.EstatisticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Estatísticas", description = "Endpoint para consulta de estatísticas das transações")
@Slf4j
@RestController
@RequestMapping("/estatistica")
@Validated
public class EstatisticaController {
    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping
    @Operation(summary = "Obtém estatísticas com base em um intervalo de tempo")
    @ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso")
    @ApiResponse(responseCode = "400", description = "Parâmetro de tempo inválido")
    public ResponseEntity<?> getEstatisticas(
            @Parameter(
                    description = "Intervalo de tempo em segundos para considerar nas estatísticas. Valor padrão = 60",
                    example = "30"
            )
            @Min(value = 1, message = "O tempo mínimo deve ser no mínimo 1 segundo.")
            @RequestParam(required = false)
            Integer time
    ){
        int tempoFinal = (time == null) ? 60 : time;
        log.info("GET /estatistica chamado");
        ResponseEntity<?> response = estatisticaService.getEstatistica(tempoFinal);
        log.info("Resposta do serviço: {}", response);
        return response;
    }
}
