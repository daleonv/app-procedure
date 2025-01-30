package com.ec.app.microservices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionVo {
    private Long transactionId;
    private Date date;
    private String transactionType;
    private Long amount;
    private Long balance;
    private Long accountId;
    private String status;

}
