package com.challengeitau.challengeitaujunior.controller;

import com.challengeitau.challengeitaujunior.service.EstatisticaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {
    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping
    public ResponseEntity<?> getEstatisticas(@RequestParam int time){
        log.info("GET /estatistica chamado");
        ResponseEntity<?> response = estatisticaService.getEstatistica(time);
        log.info("Resposta do serviço: {}", response);
        return response;
    }
}
