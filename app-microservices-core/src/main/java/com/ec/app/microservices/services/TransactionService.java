package com.ec.app.microservices.services;

import com.ec.app.entities.procedures.AccountEntity;
import com.ec.app.entities.procedures.TransactionEntity;
import com.ec.app.microservices.TransactionVo;
import com.ec.app.microservices.repositories.IAccountRepository;
import com.ec.app.microservices.repositories.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service for transaction resources
 *
 * @author daleonv
 * @version 1.0
 */
@Lazy
@Service
@Transactional
public class TransactionService implements ITransactionService {

    @Lazy
    @Autowired
    private ITransactionRepository transactionRepository;

    @Lazy
    @Autowired
    private IAccountRepository accountRepository;


    /**
     * {@inheritDoc}
     */
    @Override
    public List<TransactionEntity> findTransactionList() {
        return transactionRepository.findTransactionList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String saveTransaction(TransactionVo transaction) {
        Optional<AccountEntity> optionalAccount = accountRepository.findById(transaction.getAccountId());
        if (optionalAccount.isPresent()) {
            double balance;
            if (transaction.getTransactionType().equals("Deposito")) {
                balance = optionalAccount.get().getInitialBalance() + transaction.getAmount();
            } else {
                balance = optionalAccount.get().getInitialBalance() - transaction.getAmount();
            }
            if (balance >= 0) {
                optionalAccount.get().setInitialBalance(balance);
                accountRepository.update(optionalAccount.get());
                transactionRepository.save(TransactionEntity.builder()
                        .date(transaction.getDate())
                        .transactionType(TransactionEntity.TransactionType.Deposito)
                        .amount(transaction.getAmount())
                        .balance(balance)
                        .account(AccountEntity.builder().accountId(transaction.getAccountId()).build())
                        .status(transaction.getStatus())
                        .build());
                return "1";
            }
            return "2";
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTransaction(TransactionVo transaction) {
        Optional<TransactionEntity> optionalTransaction = transactionRepository.findById(transaction.getTransactionId());
        if (optionalTransaction.isPresent()) {
            TransactionEntity existingTransaction = getTransactionEntity(transaction, optionalTransaction);
            transactionRepository.update(existingTransaction);
        } else {
            throw new EntityNotFoundException("Transaction with ID " + transaction.getTransactionId() + " not found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTransaction(Long transactionId) {
        Optional<TransactionEntity> optionalTransaction = transactionRepository.findById(transactionId);
        optionalTransaction.ifPresent(transaction -> transactionRepository.delete(transaction));
    }


    private static TransactionEntity getTransactionEntity(TransactionVo transaction, Optional<TransactionEntity> optionalTransaction) {
        TransactionEntity existingTransaction = optionalTransaction.get();
        existingTransaction.setDate(transaction.getDate());
        existingTransaction.setTransactionType(TransactionEntity.TransactionType.Deposito);
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setBalance(transaction.getBalance());
        existingTransaction.setAccount(AccountEntity.builder().accountId(transaction.getAccountId()).build());
        existingTransaction.setStatus(transaction.getStatus());
        return existingTransaction;
    }
}
