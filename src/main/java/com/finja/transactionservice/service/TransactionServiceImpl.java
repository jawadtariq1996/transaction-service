package com.finja.transactionservice.service;

import com.finja.transactionservice.mapper.TransactionMapper;
import com.finja.transactionservice.model.dto.TransactionDto;
import com.finja.transactionservice.model.entity.Transaction;
import com.finja.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }
    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {

        Transaction transaction =  TransactionMapper.toTransaction(transactionDto);
        transaction =  transactionRepository.save(transaction);

        return TransactionMapper.toTransactionDto(transaction);

    }

    @Override
    public TransactionDto getTransaction(String transactionId) {

        Optional<Transaction> transactionOptional = transactionRepository.findById(UUID.fromString(transactionId));
       return transactionOptional.map(TransactionMapper::toTransactionDto).orElse(null);

    }
    @Override
    public List<TransactionDto> getAccountTransactions(String accountId) {
        List<Transaction> transactions =  transactionRepository.findByFromAccountIdOrToAccountId(UUID.fromString(accountId),UUID.fromString(accountId));
        return transactions.stream().map(TransactionMapper::toTransactionDto).collect(Collectors.toList());
    }

    @Override
    public void deleteTransaction(String transactionId) {
        transactionRepository.deleteById(UUID.fromString(transactionId));
    }
}
