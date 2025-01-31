package com.ec.app.microservices.services;

import com.ec.app.entities.procedures.AccountEntity;
import com.ec.app.entities.procedures.CustomerEntity;
import com.ec.app.microservices.AccountVo;
import com.ec.app.microservices.repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service for account resources
 *
 * @author daleonv
 * @version 1.0
 */
@Lazy
@Service
@Transactional
public class AccountService implements IAccountService {

    @Lazy
    @Autowired
    private IAccountRepository accountRepository;


    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccountEntity> findAccountList() {
        return accountRepository.findAccountList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountEntity findAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAccount(AccountVo account) {
        accountRepository.save(AccountEntity.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(AccountEntity.AccountType.Ahorros)
                .initialBalance(account.getInitialBalance())
                .status(account.getStatus())
                .customer(CustomerEntity.builder().customerId(account.getCustomerId()).build())
                .build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAccount(AccountVo account) {
        Optional<AccountEntity> optionalAccount = accountRepository.findById(account.getAccountId());
        if (optionalAccount.isPresent()) {
            AccountEntity existingAccount = getAccountEntity(account, optionalAccount.orElse(new AccountEntity()));
            accountRepository.update(existingAccount);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAccount(Long accountId) {
        Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);
        optionalAccount.ifPresent(account -> accountRepository.delete(account));
    }


    private static AccountEntity getAccountEntity(AccountVo account, AccountEntity optionalAccount) {
        optionalAccount.setAccountNumber(account.getAccountNumber());
        optionalAccount.setAccountType(AccountEntity.AccountType.Ahorros);
        optionalAccount.setInitialBalance(account.getInitialBalance());
        optionalAccount.setStatus(account.getStatus());
        optionalAccount.setCustomer(CustomerEntity.builder().customerId(account.getCustomerId()).build());
        return optionalAccount;
    }
}
