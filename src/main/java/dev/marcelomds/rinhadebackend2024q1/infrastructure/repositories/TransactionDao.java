package dev.marcelomds.rinhadebackend2024q1.infrastructure.repositories;

import dev.marcelomds.rinhadebackend2024q1.data.TransactionSummary;
import dev.marcelomds.rinhadebackend2024q1.domains.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionDao {

    private static final Transaction TRANSACTION_NULL_OBJECT = new Transaction(0, 0, 0, 0, "c", "", null);

    private final JdbcTemplate jdbcTemplate;

    public Transaction findLastTransaction(int clientId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM transacoes WHERE cliente_id = ? ORDER BY realizada_em DESC LIMIT 1",
                    (result, rowNum) ->
                            new Transaction(
                                    result.getLong("id"),
                                    result.getInt("cliente_id"),
                                    result.getLong("saldo"),
                                    result.getLong("valor"),
                                    result.getString("tipo"),
                                    result.getString("descricao"),
                                    result.getTimestamp("realizada_em").toLocalDateTime())
                    , clientId);
        } catch (EmptyResultDataAccessException e) {
            return TRANSACTION_NULL_OBJECT;
        }
    }

    public void insert(Transaction transaction) {
        jdbcTemplate.update("INSERT INTO transacoes (cliente_id, saldo, valor, tipo, descricao, realizada_em) VALUES (?, ?, ?, ?, ?, ?)",
                transaction.clienteId(),
                transaction.saldo(),
                transaction.valor(),
                transaction.tipo(),
                transaction.descricao(),
                transaction.realizadaEm());
    }

    public List<TransactionSummary> findLast10Transactions(int clientId) {
        return jdbcTemplate.query("SELECT * FROM transacoes WHERE cliente_id = ? ORDER BY realizada_em DESC LIMIT 10",
                (result, rowNum) ->
                        new TransactionSummary(
                                result.getInt("saldo"),
                                result.getInt("valor"),
                                result.getString("tipo").charAt(0),
                                result.getString("descricao"),
                                result.getTimestamp("realizada_em").toLocalDateTime())
                , clientId);
    }
}
