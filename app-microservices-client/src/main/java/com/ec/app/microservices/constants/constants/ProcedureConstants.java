package com.ec.app.microservices.constants.constants;

import com.ec.app.microservices.resources.ProcedureProperties;

/**
 * Procedure constants values used throughout the application
 *
 * @author daleonv
 * @version 1.0
 */

public class ProcedureConstants {

    public static final String CREATED_MESSAGE = ProcedureProperties.
            getString("com.ec.sig.person.created.status.message");
    public static final String UPDATED_MESSAGE = ProcedureProperties.
            getString("com.ec.sig.person.updated.status.message");
    public static final String DELETED_MESSAGE = ProcedureProperties.
            getString("com.ec.sig.person.deleted.status.message");
    public static final String INSUFFICIENT_FUNDS = ProcedureProperties.
            getString("com.ec.sig.person.insufficient.funds.status.message");
    public static final String DEPOSIT_TRANSACTION = ProcedureProperties.
            getString("com.ec.sig.procedure.deposit.transaction");
    public static final String ACCOUNT_NOT_FOUND = ProcedureProperties.
            getString("com.ec.sig.procedure.account.not.found");
}
