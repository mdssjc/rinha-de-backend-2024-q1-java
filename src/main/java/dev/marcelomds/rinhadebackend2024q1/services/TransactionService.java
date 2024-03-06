package dev.marcelomds.rinhadebackend2024q1.services;

import dev.marcelomds.rinhadebackend2024q1.data.Balance;
import dev.marcelomds.rinhadebackend2024q1.data.StatementResponse;
import dev.marcelomds.rinhadebackend2024q1.data.TransactionRequest;
import dev.marcelomds.rinhadebackend2024q1.data.TransactionResponse;
import dev.marcelomds.rinhadebackend2024q1.domains.Client;
import dev.marcelomds.rinhadebackend2024q1.domains.Transaction;
import dev.marcelomds.rinhadebackend2024q1.infrastructure.repositories.TransactionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionDao transactionDao;

    public TransactionResponse credit(Client client, TransactionRequest request) {
        var lastTransaction = transactionDao.findLastTransaction(client.id());
        return createTransaction(client, request, lastTransaction.saldo() + request.valor());
    }

    public TransactionResponse debit(Client client, TransactionRequest request) {
        var lastTransaction = transactionDao.findLastTransaction(client.id());

        var newBalance = lastTransaction.saldo() - request.valor();
        if (newBalance + client.limite() < 0) {
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return createTransaction(client, request, newBalance);
    }

    public StatementResponse summary(Client client) {
        var transactionSummaries = transactionDao.findLast10Transactions(client.id());
        var total = transactionSummaries.isEmpty() ? 0L : transactionSummaries.getFirst().saldo();
        var balance = new Balance(total, LocalDateTime.now(), client.limite());
        return new StatementResponse(balance, transactionSummaries);
    }

    private TransactionResponse createTransaction(Client client, TransactionRequest request, long newBalance) {
        transactionDao.insert(Transaction.of(client.id(), newBalance, request));
        return new TransactionResponse(client.limite(), newBalance);
    }
}
