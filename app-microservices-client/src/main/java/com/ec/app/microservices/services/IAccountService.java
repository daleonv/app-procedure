package com.ec.app.microservices.services;

import com.ec.app.entities.procedures.AccountEntity;
import com.ec.app.microservices.AccountVo;

import java.util.List;

/**
 * Service interface for account resources
 *
 * @author dalonv
 * @version 1.0
 */
public interface IAccountService {

    /**
     * Return account list information
     *
     * @return List<AccountEntity>
     */
    List<AccountEntity> findAccountList();

    /**
     * Save account
     *
     * @param account AccountVo
     */
    void saveAccount(AccountVo account);

    /**
     * Update account information.
     *
     * @param account AccountVo
     */
    void updateAccount(AccountVo account);

    /**
     * Delete an account.
     *
     * @param accountId Long
     */
    void deleteAccount(Long accountId);

}
