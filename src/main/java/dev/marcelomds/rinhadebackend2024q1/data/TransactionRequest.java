package dev.marcelomds.rinhadebackend2024q1.data;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotNull(message = "O valor não pode ser vazia")
        @Digits(integer = 12, fraction = 0, message = "O valor não pode contém centavos")
        BigDecimal valor,
        @NotEmpty(message = "O tipo não pode ser vazia")
        @Pattern(regexp = "[cdCD]", message = "O tipo deve ser 'c' ou 'd'")
        String tipo,
        @NotEmpty(message = "A descrição não pode ser vazia")
        @Length(min = 1, max = 10, message = "A descrição deve ter entre 1 e 10 caracteres")
        String descricao
) {
    public char typeCoerced() {
        return tipo.charAt(0);
    }

    public long amountCoerced() {
        return valor.longValue() * 100;
    }
}
