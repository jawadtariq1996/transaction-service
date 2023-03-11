package com.finja.transactionservice;

import com.finja.transactionservice.constants.TransactionConstant;
import com.finja.transactionservice.model.dto.TransactionDto;
import com.finja.transactionservice.model.entity.Transaction;
import com.finja.transactionservice.repository.TransactionRepository;
import com.finja.transactionservice.service.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceImplTest {

    private TransactionRepository transactionRepository;
    private TransactionServiceImpl transactionService;
    @BeforeEach
    public void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionServiceImpl(transactionRepository);
    }
    @Test
    public void testCreateTransaction() {
        // given
        UUID toAccountId = UUID.randomUUID();
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(BigDecimal.valueOf(100.0));
        transactionDto.setFromAccountId(UUID.fromString(TransactionConstant.FROM_ACCOUNT));
        transactionDto.setToAccountId(toAccountId);

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setFromAccountId(UUID.fromString(TransactionConstant.FROM_ACCOUNT));
        transaction.setToAccountId(toAccountId);
        transaction.setCreatedAt(LocalDateTime.now());

        when(transactionRepository.save(any())).thenReturn(transaction);

        // when
        TransactionDto createdTransaction = transactionService.createTransaction(transactionDto);

        // then
        assertNotNull(createdTransaction);
        assertNotNull(createdTransaction.getTransactionId());
        assertEquals(transactionDto.getAmount(), createdTransaction.getAmount());
        assertEquals(transactionDto.getFromAccountId(), createdTransaction.getFromAccountId());
        assertEquals(transactionDto.getToAccountId(), createdTransaction.getToAccountId());
    }

    @Test
    public void testGetTransaction() {

        UUID fromAccountId = UUID.randomUUID();

        // Given
        UUID transactionId = UUID.randomUUID();

        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setFromAccountId(fromAccountId);
        transaction.setToAccountId(UUID.fromString(TransactionConstant.FROM_ACCOUNT));
        transaction.setTransactionId(transactionId);

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        // When
        TransactionDto foundTransactionDto = transactionService.getTransaction(transactionId.toString());

        // Then
        assertEquals(transaction.getAmount(), foundTransactionDto.getAmount());
        assertEquals(transaction.getFromAccountId(), foundTransactionDto.getFromAccountId());
        assertEquals(transaction.getToAccountId(), foundTransactionDto.getToAccountId());
        assertEquals(transaction.getTransactionId(), foundTransactionDto.getTransactionId());
    }

    @Test
    public void testGetAccountTransactions() {
        // Given
        UUID accountId = UUID.randomUUID();

        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(BigDecimal.valueOf(100.0));
        transaction1.setFromAccountId(accountId);
        transaction1.setToAccountId(UUID.randomUUID());
        transaction1.setTransactionId(UUID.randomUUID());
        transactions.add(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(BigDecimal.valueOf(200.0));
        transaction2.setFromAccountId(UUID.randomUUID());
        transaction2.setToAccountId(accountId);
        transaction2.setTransactionId(UUID.randomUUID());
        transactions.add(transaction2);

        when(transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId)).thenReturn(transactions);

        // When
        List<TransactionDto> foundTransactionDtos = transactionService.getAccountTransactions(accountId.toString());

        // Then
        assertEquals(transactions.size(), foundTransactionDtos.size());
        assertEquals(transactions.get(0).getAmount(), foundTransactionDtos.get(0).getAmount());
        assertEquals(transactions.get(0).getFromAccountId(), foundTransactionDtos.get(0).getFromAccountId());
        assertEquals(transactions.get(0).getToAccountId(), foundTransactionDtos.get(0).getToAccountId());
        assertEquals(transactions.get(0).getTransactionId(), foundTransactionDtos.get(0).getTransactionId());

    }



    }

