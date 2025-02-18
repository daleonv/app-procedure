package com.ec.app.microservices.services;

import com.ec.app.entities.procedures.TransactionEntity;
import com.ec.app.microservices.TransactionVo;
import com.ec.app.microservices.config.Response;

import java.util.List;

/**
 * Service interface for transaction resources
 *
 * @author daleonv
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
     * @return Response<String>
     */
    Response<String> saveTransaction(TransactionVo transaction);

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
