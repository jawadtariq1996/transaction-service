package com.finja.transactionservice.service;

import com.finja.transactionservice.model.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto transactionDto);

    TransactionDto getTransaction(String transactionId);
    List<TransactionDto> getAccountTransactions(String accountId);
    void deleteTransaction(String transactionId);
}
