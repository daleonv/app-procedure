package com.ec.app.microservices.controllers;

import com.ec.app.entities.procedures.TransactionEntity;
import com.ec.app.microservices.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void testFindTransactionList_Success() throws Exception {
        TransactionEntity transaction1 = TransactionEntity.builder().transactionId(1L).build();
        TransactionEntity transaction2 = TransactionEntity.builder().transactionId(2L).build();
        List<TransactionEntity> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionService.findTransactionList()).thenReturn(transactions);

        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].transactionId").value(1))
                .andExpect(jsonPath("$.data[1].transactionId").value(2));
    }

    @Test
    public void testFindTransactionList_Empty() throws Exception {
        when(transactionService.findTransactionList()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }


}
