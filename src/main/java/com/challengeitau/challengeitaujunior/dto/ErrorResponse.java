package com.challengeitau.challengeitaujunior.dto;

import java.time.OffsetDateTime;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        OffsetDateTime timestamp
) {
}
