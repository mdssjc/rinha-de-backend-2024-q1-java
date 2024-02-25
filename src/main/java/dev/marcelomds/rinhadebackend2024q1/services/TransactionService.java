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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionDao transactionDao;

    @Transactional
    public TransactionResponse credit(Client client, TransactionRequest request) {
        var lastTransaction = transactionDao.findLastTransaction(client.id());
        return createTransaction(client, request, lastTransaction.saldo() + request.amountCoerced());
    }

    @Transactional
    public TransactionResponse debit(Client client, TransactionRequest request) {
        var lastTransaction = transactionDao.findLastTransaction(client.id());

        var newBalance = lastTransaction.saldo() - request.amountCoerced();
        if (Math.abs(newBalance) <= client.limite()) {
            return createTransaction(client, request, newBalance);
        }

        throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public StatementResponse summary(Client client) {
        var transactionSummaries = transactionDao.findLast10Transactions(client.id());
        var balance = new Balance(transactionSummaries.getFirst().saldo(), LocalDateTime.now(), client.limite());
        return new StatementResponse(balance, transactionSummaries);
    }

    private TransactionResponse createTransaction(Client client, TransactionRequest request, long newBalance) {
        transactionDao.insert(Transaction.of(client.id(), newBalance, request));
        return new TransactionResponse(client.limite(), newBalance);
    }
}
