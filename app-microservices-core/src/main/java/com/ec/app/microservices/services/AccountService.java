package com.ec.app.microservices.services;

import com.ec.app.entities.procedures.AccountEntity;
import com.ec.app.entities.procedures.CustomerEntity;
import com.ec.app.microservices.AccountVo;
import com.ec.app.microservices.repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
            AccountEntity existingAccount = getAccountEntity(account, optionalAccount);
            accountRepository.update(existingAccount);
        } else {
            throw new EntityNotFoundException("Account with ID " + account.getAccountId() + " not found");
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


    private static AccountEntity getAccountEntity(AccountVo account, Optional<AccountEntity> optionalAccount) {
        AccountEntity existingAccount = optionalAccount.get();
        existingAccount.setAccountNumber(account.getAccountNumber());
        existingAccount.setAccountType(AccountEntity.AccountType.Ahorros);
        existingAccount.setInitialBalance(account.getInitialBalance());
        existingAccount.setStatus(account.getStatus());
        existingAccount.setCustomer(CustomerEntity.builder().customerId(account.getCustomerId()).build());
        return existingAccount;
    }
}
