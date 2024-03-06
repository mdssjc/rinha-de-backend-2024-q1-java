package dev.marcelomds.rinhadebackend2024q1.services;

import dev.marcelomds.rinhadebackend2024q1.data.StatementResponse;
import dev.marcelomds.rinhadebackend2024q1.data.TransactionRequest;
import dev.marcelomds.rinhadebackend2024q1.data.TransactionResponse;
import dev.marcelomds.rinhadebackend2024q1.domains.Client;
import dev.marcelomds.rinhadebackend2024q1.infrastructure.repositories.ClientDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Service
public class OperationService {

    private final ClientDao clientDao;
    private final TransactionService transactionService;

    @Transactional
    public TransactionResponse transaction(int clientId, TransactionRequest request) {
        var client = getClient(clientId);
        if ("c".equalsIgnoreCase(request.tipo())) {
            return transactionService.credit(client, request);
        }
        return transactionService.debit(client, request);
    }

    @Transactional(readOnly = true)
    public StatementResponse statement(int clientId) {
        return transactionService.summary(getClient(clientId));
    }

    private Client getClient(int clientId) {
        return clientDao.findById(clientId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
                        "Cliente %d não encontrado!".formatted(clientId)));
    }
}
