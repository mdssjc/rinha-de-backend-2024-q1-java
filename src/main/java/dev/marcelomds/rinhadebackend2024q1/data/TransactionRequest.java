package dev.marcelomds.rinhadebackend2024q1.data;

import dev.marcelomds.rinhadebackend2024q1.data.validation.Type;
import jakarta.validation.constraints.DecimalMin;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record TransactionRequest(
        @DecimalMin(value = "0", message = "O valor não pode contém centavos")
        BigDecimal valor,
        @Type
        String tipo,
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
