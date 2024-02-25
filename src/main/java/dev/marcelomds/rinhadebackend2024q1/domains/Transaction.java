package dev.marcelomds.rinhadebackend2024q1.domains;

import dev.marcelomds.rinhadebackend2024q1.data.TransactionRequest;

import java.time.LocalDateTime;

public record Transaction(
        long id,
        int clienteId,
        long saldo,

        long valor,
        char tipo,
        String descricao,

        LocalDateTime realizadaEm
) {

    public static Transaction of(int clientId, long newBalance, TransactionRequest transactionRequest) {
        return new Transaction(
                -1,
                clientId,
                newBalance,
                transactionRequest.amountCoerced(),
                transactionRequest.typeCoerced(),
                transactionRequest.descricao(),
                LocalDateTime.now()
        );
    }
}
