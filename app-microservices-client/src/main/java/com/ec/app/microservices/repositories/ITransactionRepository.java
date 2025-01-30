package com.ec.app.microservices.repositories;

import com.ec.app.entities.procedures.TransactionEntity;
import com.ec.app.microservices.config.IQueryDslBaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for transaction resources
 *
 * @author daleonv
 * @version 1.0
 */
public interface ITransactionRepository extends IQueryDslBaseRepository<TransactionEntity> {

    /**
     * Return transaction list information
     *
     * @return List<TransactionEntity>
     */
    List<TransactionEntity> findTransactionList();

    /**
     * Return transaction information by transactionId
     *
     * @return Optional<TransactionEntity>
     */
    Optional<TransactionEntity> findById(Long transactionId);

}
