package com.finja.transactionservice.controller;

import com.finja.transactionservice.model.dto.TransactionDto;
import com.finja.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @PostMapping("/transaction/{accountId}")
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable String accountId, @RequestBody TransactionDto transactionDto){
        transactionDto.setToAccountId(UUID.fromString(accountId));
        TransactionDto transactionDtoResponse =  transactionService.createTransaction(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDtoResponse);
    }

    @DeleteMapping("/transaction/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable String transactionId){
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable String transactionId){
        TransactionDto transactionDto = transactionService.getTransaction(transactionId);
        return ResponseEntity.ok(transactionDto);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDto>> fetchAccountTransactions(@RequestParam String accountId){
        List<TransactionDto> transactionDto = transactionService.getAccountTransactions(accountId);
        return ResponseEntity.ok(transactionDto);
    }
}
