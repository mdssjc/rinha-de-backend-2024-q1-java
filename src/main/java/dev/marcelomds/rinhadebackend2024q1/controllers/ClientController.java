package dev.marcelomds.rinhadebackend2024q1.controllers;

import dev.marcelomds.rinhadebackend2024q1.data.StatementResponse;
import dev.marcelomds.rinhadebackend2024q1.data.TransactionRequest;
import dev.marcelomds.rinhadebackend2024q1.data.TransactionResponse;
import dev.marcelomds.rinhadebackend2024q1.services.OperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes/{id}")
public class ClientController {

    private final OperationService operationService;

    @PostMapping("/transacoes")
    public TransactionResponse transactions(@PathVariable int id, @Valid @RequestBody TransactionRequest request) {
        return operationService.transaction(id, request);
    }

    @GetMapping("/extrato")
    public StatementResponse transactions(@PathVariable int id) {
        return operationService.statement(id);
    }
}
