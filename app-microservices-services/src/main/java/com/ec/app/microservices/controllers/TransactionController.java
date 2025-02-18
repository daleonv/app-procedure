package com.ec.app.microservices.controllers;

import com.ec.app.entities.procedures.TransactionEntity;
import com.ec.app.microservices.TransactionVo;
import com.ec.app.microservices.config.Response;
import com.ec.app.microservices.constants.constants.ProcedureConstants;
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

    @PostMapping("create")
    public ResponseEntity<Response<String>> saveTransaction(@RequestBody TransactionVo transaction) {
        Response<String> response = transactionService.saveTransaction(transaction);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Response<Void>> updateTransaction(@PathVariable Long transactionId,
                                                            @RequestBody TransactionVo transaction) {
        transaction.setTransactionId(transactionId);
        transactionService.updateTransaction(transaction);
        return new ResponseEntity<>(Response.<Void>builder()
                .code(HttpStatus.OK.value())
                .message(ProcedureConstants.UPDATED_MESSAGE)
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Response<Void>> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return new ResponseEntity<>(Response.<Void>builder()
                .code(HttpStatus.OK.value())
                .message(ProcedureConstants.DELETED_MESSAGE)
                .build(), HttpStatus.OK);
    }

}
