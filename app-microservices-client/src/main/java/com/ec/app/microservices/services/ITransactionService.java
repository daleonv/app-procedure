package com.ec.app.microservices.services;

import com.ec.app.entities.procedures.TransactionEntity;
import com.ec.app.microservices.TransactionVo;

import java.util.List;

/**
 * Service interface for transaction resources
 *
 * @author dalonv
 * @version 1.0
 */
public interface ITransactionService {

    /**
     * Return transaction list information
     *
     * @return List<TransactionEntity>
     */
    List<TransactionEntity> findTransactionList();

    /**
     * Save transaction
     *
     * @param transaction TransactionVo
     * @return String
     */
    String saveTransaction(TransactionVo transaction);

    /**
     * Update transaction information.
     *
     * @param transaction TransactionVo
     */
    void updateTransaction(TransactionVo transaction);

    /**
     * Delete a transaction.
     *
     * @param transactionId Long
     */
    void deleteTransaction(Long transactionId);

}
