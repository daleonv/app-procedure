package com.ec.app.microservices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountVo {

    private Long accountId;
    private String accountNumber;
    private String accountType;
    private Long initialBalance;
    private String status;
private Long customerId;

}
