package com.ec.app.microservices.repositories;

import com.ec.app.entities.procedures.AccountEntity;
import com.ec.app.microservices.config.IQueryDslBaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interface for account resources
 *
 * @author daleonv
 * @version 1.0
 */
public interface IAccountRepository extends IQueryDslBaseRepository<AccountEntity> {

    /**
     * Return account list information
     *
     * @return List<AccountEntity>
     */
    List<AccountEntity> findAccountList();

    /**
     * Return account information by accountId
     *
     * @param accountId Long
     * @return Optional<AccountEntity>
     */
    Optional<AccountEntity> findById(Long accountId);

}
