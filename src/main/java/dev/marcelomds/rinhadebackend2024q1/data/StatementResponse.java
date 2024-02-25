package dev.marcelomds.rinhadebackend2024q1.data;

import java.util.List;

public record StatementResponse(
        Balance saldo,
        List<TransactionSummary> ultimasTransacoes
) {
}
