package com.ec.app.microservices.services;

import com.ec.app.entities.procedures.AccountEntity;
import com.ec.app.entities.procedures.TransactionEntity;
import com.ec.app.microservices.TransactionVo;
import com.ec.app.microservices.repositories.IAccountRepository;
import com.ec.app.microservices.repositories.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Comparator;
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
    @Transactional
    public String saveTransaction(TransactionVo transaction) {
        Optional<AccountEntity> optionalAccount = accountRepository.findById(transaction.getAccountId());
        List<TransactionEntity> transactionList = this.findTransactionList();

        if (optionalAccount.isPresent()) {
            Optional<TransactionEntity> latestTransaction = transactionList.stream()
                    .filter(t -> t.getAccount().getAccountId().equals(optionalAccount.get().getAccountId()))
                    .max(Comparator.comparing(TransactionEntity::getDate));

            AccountEntity account = optionalAccount.get();
            double newBalance;

            if (latestTransaction.isPresent()) {
                if (transaction.getTransactionType().equals("Deposito")) {
                    newBalance = latestTransaction.get().getBalance() + transaction.getAmount();
                } else {
                    newBalance = latestTransaction.get().getBalance() - transaction.getAmount();
                    if (newBalance < 0) {
                        return "2";
                    }
                }
            } else {
                if (transaction.getTransactionType().equals("Deposito")) {
                    newBalance = account.getInitialBalance() + transaction.getAmount();
                } else {
                    newBalance = account.getInitialBalance() - transaction.getAmount();
                    if (newBalance < 0) {
                        return "2";
                    }
                }
            }

            TransactionEntity transactionEntity = TransactionEntity.builder()
                    .date(transaction.getDate())
                    .transactionType(TransactionEntity.TransactionType.valueOf(transaction.getTransactionType()))
                    .amount(transaction.getAmount())
                    .balance(newBalance)
                    .account(account)
                    .status(transaction.getStatus())
                    .build();

            transactionRepository.save(transactionEntity);

            return "1";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTransaction(TransactionVo transaction) {
        Optional<TransactionEntity> optionalTransaction = transactionRepository.findById(transaction.getTransactionId());
        if (optionalTransaction.isPresent()) {
            TransactionEntity existingTransaction = getTransactionEntity(transaction,
                    optionalTransaction.orElse(new TransactionEntity()));
            transactionRepository.update(existingTransaction);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteTransaction(Long transactionId) {
        Optional<TransactionEntity> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isPresent()) {
            optionalTransaction.ifPresent(transaction -> transactionRepository.delete(transaction));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    private static TransactionEntity getTransactionEntity(TransactionVo transaction, TransactionEntity optionalTransaction) {
        optionalTransaction.setDate(transaction.getDate());
        optionalTransaction.setTransactionType(TransactionEntity.TransactionType.valueOf(transaction.getTransactionType()));
        optionalTransaction.setAmount(transaction.getAmount());
        optionalTransaction.setBalance(transaction.getBalance());
        optionalTransaction.setAccount(AccountEntity.builder().accountId(transaction.getAccountId()).build());
        optionalTransaction.setStatus(transaction.getStatus());
        return optionalTransaction;
    }
}
