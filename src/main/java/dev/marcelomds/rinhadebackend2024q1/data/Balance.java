package dev.marcelomds.rinhadebackend2024q1.data;

import java.time.LocalDateTime;

public record Balance(
        long total,
        LocalDateTime dataExtrato,
        long limite
) {
}
