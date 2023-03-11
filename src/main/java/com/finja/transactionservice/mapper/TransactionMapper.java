package com.finja.transactionservice.mapper;

import com.finja.transactionservice.constants.TransactionStatus;
import com.finja.transactionservice.constants.TransactionType;
import com.finja.transactionservice.model.dto.TransactionDto;
import com.finja.transactionservice.model.entity.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.finja.transactionservice.constants.TransactionConstant.CREATED_BY_USER;
import static com.finja.transactionservice.constants.TransactionConstant.FROM_ACCOUNT;

public class TransactionMapper {

    public static Transaction toTransaction(TransactionDto transactionDto){
        Transaction transaction = new Transaction();

        transaction.setTransactionId(UUID.randomUUID());
        transaction.setCustomerId(transactionDto.getCustomerId());
        transaction.setFromAccountId(UUID.fromString(FROM_ACCOUNT));
        transaction.setToAccountId(transactionDto.getToAccountId());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setCurrency(transactionDto.getCurrencyCode());
        transaction.setTransactionType(TransactionType.ONLINE.toString());
        transaction.setTransactionStatus(TransactionStatus.SUCCESS.toString());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setCreatedBy(CREATED_BY_USER);

        return transaction;
    }
    public static TransactionDto toTransactionDto(Transaction transaction){
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setTransactionId(transaction.getTransactionId());
        transactionDto.setCustomerId(transaction.getCustomerId());
        transactionDto.setFromAccountId(transaction.getFromAccountId());
        transactionDto.setToAccountId(transaction.getToAccountId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setCurrencyCode(transaction.getCurrency());
        transactionDto.setTransactionType(transaction.getTransactionType());
        transactionDto.setTransactionStatus(transaction.getTransactionStatus());
        transactionDto.setCreatedAt(LocalDateTime.now());
        transactionDto.setCreatedBy(CREATED_BY_USER);

        return transactionDto;
    }

}
