package com.ec.app.microservices.services;

import com.ec.app.entities.procedures.AccountEntity;
import com.ec.app.entities.procedures.TransactionEntity;
import com.ec.app.microservices.TransactionVo;
import com.ec.app.microservices.config.Response;
import com.ec.app.microservices.constants.constants.ProcedureConstants;
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

    @Override
    public Response<String> saveTransaction(TransactionVo transaction) {
        AccountEntity account = accountRepository.findById(transaction.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ProcedureConstants.ACCOUNT_NOT_FOUND));

        double newBalance = calculateNewBalance(account, transaction);

        if (newBalance < 0) {
            return Response.<String>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(ProcedureConstants.INSUFFICIENT_FUNDS)
                    .build();
        }

        saveTransactionEntity(transaction, account, newBalance);

        return Response.<String>builder()
                .code(HttpStatus.CREATED.value())
                .message(ProcedureConstants.CREATED_MESSAGE)
                .build();
    }

    /**
     * Calculate new balance
     */
    private double calculateNewBalance(AccountEntity account, TransactionVo transaction) {
        List<TransactionEntity> transactionList = this.findTransactionList();

        Optional<TransactionEntity> latestTransaction = transactionList.stream()
                .filter(t -> t.getAccount().getAccountId().equals(account.getAccountId()))
                .max(Comparator.comparing(TransactionEntity::getDate));

        double previousBalance = latestTransaction.map(TransactionEntity::getBalance)
                .orElse(account.getInitialBalance());

        return transaction.getTransactionType().equals(ProcedureConstants.DEPOSIT_TRANSACTION)
                ? previousBalance + transaction.getAmount()
                : previousBalance - transaction.getAmount();
    }

    /**
     * Save transaction in database
     */
    private void saveTransactionEntity(TransactionVo transaction, AccountEntity account, double newBalance) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .date(transaction.getDate())
                .transactionType(TransactionEntity.TransactionType.valueOf(transaction.getTransactionType()))
                .amount(transaction.getAmount())
                .balance(newBalance)
                .account(account)
                .status(transaction.getStatus())
                .build();

        transactionRepository.save(transactionEntity);
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

    /**
     * Get transaction entity
     */
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
