package dev.marcelomds.rinhadebackend2024q1.data;

import jakarta.validation.ValidationException;

public record TransactionRequest(
        Long valor,
        String tipo,
        String descricao
) {
    public TransactionRequest {
        if (valor == null || valor < 1) {
            throw new ValidationException("O valor está inválido!");
        }
        if (!"c".equalsIgnoreCase(tipo) && !"d".equalsIgnoreCase(tipo)) {
            throw new ValidationException("O tipo está inválido!");
        }
        if (descricao == null || descricao.isBlank() || descricao.isEmpty() || descricao.length() > 10) {
            throw new ValidationException("A descrição está inválida!");
        }
    }
}
