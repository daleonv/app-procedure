package com.ec.app.microservices.repositories;

import com.ec.app.entities.procedures.AccountEntity;
import com.ec.app.microservices.config.JPAQueryDslBaseRepository;
import com.querydsl.core.types.Projections;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ec.app.entities.procedures.QAccountEntity.accountEntity;

/**
 * Repository for account resources
 *
 * @author daleonv
 * @version 1.0
 */
@Lazy
@Repository
public class AccountRepository extends JPAQueryDslBaseRepository<AccountEntity> implements IAccountRepository {
    public AccountRepository() {
        super(AccountEntity.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccountEntity> findAccountList() {
        return from(accountEntity).select(Projections.bean(AccountEntity.class,
                                accountEntity.accountId,
                                accountEntity.accountNumber,
                                accountEntity.accountType,
                                accountEntity.initialBalance,
                                accountEntity.status
                        )
                )
                .stream().toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AccountEntity> findById(Long accountId) {
        return from(accountEntity)
                .where(accountEntity.accountId.eq(accountId))
                .select(Projections.bean(AccountEntity.class,
                        accountEntity.accountId,
                        accountEntity.accountNumber,
                        accountEntity.accountType,
                        accountEntity.initialBalance,
                        accountEntity.status,
                        accountEntity.customer
                ))
                .stream().findFirst();
    }

}
