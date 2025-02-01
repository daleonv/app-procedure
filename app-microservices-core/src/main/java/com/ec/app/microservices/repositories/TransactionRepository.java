package com.ec.app.microservices.repositories;

import com.ec.app.entities.procedures.AccountEntity;
import com.ec.app.entities.procedures.QAccountEntity;
import com.ec.app.entities.procedures.TransactionEntity;
import com.ec.app.microservices.config.JPAQueryDslBaseRepository;
import com.querydsl.core.types.Projections;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ec.app.entities.procedures.QTransactionEntity.transactionEntity;

/**
 * Repository for transaction resources
 *
 * @author daleonv
 * @version 1.0
 */
@Lazy
@Repository
public class TransactionRepository extends JPAQueryDslBaseRepository<TransactionEntity> implements ITransactionRepository {
    public TransactionRepository() {
        super(TransactionEntity.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TransactionEntity> findTransactionList() {
        QAccountEntity account = QAccountEntity.accountEntity;
        return from(transactionEntity).select(Projections.bean(TransactionEntity.class,
                                transactionEntity.transactionId,
                                transactionEntity.date,
                                transactionEntity.transactionType,
                                transactionEntity.amount,
                                transactionEntity.balance,
                                Projections.bean(AccountEntity.class,
                                        account.accountId,
                                        account.accountNumber,
                                        account.accountType,
                                        account.initialBalance
                                ).as("account"),
                                transactionEntity.status
                        )
                )
                .innerJoin(transactionEntity.account, account)
                .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TransactionEntity> findById(Long transactionId) {
        return from(transactionEntity)
                .where(transactionEntity.transactionId.eq(transactionId))
                .select(Projections.bean(TransactionEntity.class,
                        transactionEntity.date,
                        transactionEntity.transactionType,
                        transactionEntity.amount,
                        transactionEntity.balance,
                        transactionEntity.status
                ))
                .stream().findFirst();
    }

}
