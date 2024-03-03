package dev.marcelomds.rinhadebackend2024q1.data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotNull(message = "O valor não pode ser null")
        @DecimalMin(value = "0", message = "O valor não pode contém centavos")
        BigDecimal valor,
        @NotNull(message = "O tipo não pode ser null")
        @Pattern(regexp = "[cd]", message = "O tipo deve ser 'c' ou 'd'")
        String tipo,
        @NotNull(message = "A descrição não pode ser null")
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
