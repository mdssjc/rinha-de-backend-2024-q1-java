package dev.marcelomds.rinhadebackend2024q1.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public record TransactionSummary(
        @JsonIgnore
        long saldo,
        long valor,
        String tipo,
        String descricao,
        LocalDateTime realizadaEm
) {
}
