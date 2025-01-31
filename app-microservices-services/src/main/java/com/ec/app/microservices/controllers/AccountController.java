package com.ec.app.microservices.controllers;

import com.ec.app.entities.procedures.AccountEntity;
import com.ec.app.microservices.AccountVo;
import com.ec.app.microservices.config.Response;
import com.ec.app.microservices.services.IAccountService;
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
@RequestMapping("account")
@Lazy
public class AccountController {
    @Lazy
    @Autowired
    private IAccountService accountService;

    @GetMapping("")
    public ResponseEntity<Response<List<AccountEntity>>> findAccountList() {
        return new ResponseEntity<>(Response.<List<AccountEntity>>builder()
                .data(accountService.findAccountList())
                .code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Response<AccountEntity>> findAccount(@PathVariable Long accountId) {
        return new ResponseEntity<>(Response.<AccountEntity>builder()
                .data(accountService.findAccount(accountId))
                .code(HttpStatus.OK.value())
                .build(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Response<Void>> saveAccount(
            @RequestBody AccountVo account) throws IOException {
        accountService.saveAccount(account);
        return new ResponseEntity<>(Response.<Void>builder()
                .code(HttpStatus.CREATED.value())
                .message("Creado con éxito")
                .build(), HttpStatus.CREATED);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Response<Void>> updateAccount(@PathVariable Long accountId,
                                                        @RequestBody AccountVo account) {
        account.setAccountId(accountId);
        accountService.updateAccount(account);
        return new ResponseEntity<>(Response.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Actualizado con éxito")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Response<Void>> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(Response.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Eliminado con éxito")
                .build(), HttpStatus.OK);
    }

}
