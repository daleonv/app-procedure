package com.ec.app.microservices.controllers;

import com.ec.app.entities.procedures.TransactionEntity;
import com.ec.app.microservices.TransactionVo;
import com.ec.app.microservices.config.Response;
import com.ec.app.microservices.services.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("transaction")
@Lazy
public class TransactionController {
    @Lazy
    @Autowired
    private ITransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<Response<List<TransactionEntity>>> findTransactionList() {
        return new ResponseEntity<>(Response.<List<TransactionEntity>>builder()
                .data(transactionService.findTransactionList())
                .code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Response<Void>> saveTransaction(
            @RequestBody TransactionVo transaction) throws IOException {
        String response = transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(Response.<Void>builder()
                .code(response.equals("1") ? HttpStatus.CREATED.value() : HttpStatus.BAD_REQUEST.value())
                .message(response.equals("1") ? "Creado" : "No cuenta con los fondos suficientes")
                .build(), response.equals("1") ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Response<Void>> updateTransaction(@PathVariable Long transactionId,
                                                            @RequestBody TransactionVo transaction) {
        transaction.setTransactionId(transactionId);
        transactionService.updateTransaction(transaction);
        return new ResponseEntity<>(Response.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Actualizado con éxito")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok().build();
    }

}
