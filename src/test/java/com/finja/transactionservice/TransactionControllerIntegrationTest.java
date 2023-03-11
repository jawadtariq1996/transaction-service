package com.finja.transactionservice;

import com.finja.transactionservice.model.dto.TransactionDto;
import com.finja.transactionservice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TransactionService transactionService;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/transaction";
    }

    @Test
    public void testCreateTransaction() {
        // Given
        UUID accountId = UUID.randomUUID();
        TransactionDto transactionDto = new TransactionDto();
        // Set transactionDto properties here
        transactionDto.setCustomerId(UUID.fromString("b40e3e8f-9ec4-4201-a3f8-ce4c188aa4a5"));
        transactionDto.setAmount(BigDecimal.valueOf(3000.0));
        transactionDto.setCurrencyCode("USD");

        // When
        ResponseEntity<TransactionDto> response = restTemplate.postForEntity(baseUrl + "/" + accountId, transactionDto, TransactionDto.class);
        TransactionDto transactionDtoResponse = response.getBody();
        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getTransactionId());

        // Check other properties here
        assertNotNull(transactionDtoResponse);
        assertEquals(transactionDto.getAmount(),transactionDtoResponse.getAmount());
        assertEquals(transactionDto.getCurrencyCode(),transactionDtoResponse.getCurrencyCode());


    }

    @Test
    public void testDeleteTransaction() {
        // Given
        UUID accountId = UUID.randomUUID();
        TransactionDto transactionDto = createTransaction(accountId);
        UUID transactionId = transactionDto.getTransactionId();

        // When
        ResponseEntity<Void> response = restTemplate.exchange(baseUrl + "/" + transactionId, HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(transactionService.getTransaction(transactionId.toString()));
    }

    @Test
    public void testGetTransaction() {
        // Given
        UUID accountId = UUID.randomUUID();
        TransactionDto transactionResponseDto =  createTransaction(accountId);
        UUID transactionId = transactionResponseDto.getTransactionId();

        // When
        ResponseEntity<TransactionDto> response = restTemplate.getForEntity(baseUrl + "/" + transactionId, TransactionDto.class);
        TransactionDto getTransactionResponse = response.getBody();
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // Check transactionDto properties here

        assertNotNull(getTransactionResponse);
        assertEquals(transactionResponseDto.getTransactionId(),getTransactionResponse.getTransactionId());
        assertEquals(transactionResponseDto.getCustomerId(),getTransactionResponse.getCustomerId());
        assertEquals(transactionResponseDto.getFromAccountId(),getTransactionResponse.getFromAccountId());
        assertEquals(transactionResponseDto.getToAccountId(),getTransactionResponse.getToAccountId());
        assertEquals(transactionResponseDto.getTransactionType(),getTransactionResponse.getTransactionType());
        assertEquals(transactionResponseDto.getTransactionStatus(),getTransactionResponse.getTransactionStatus());
        assertEquals(transactionResponseDto.getCurrencyCode(),getTransactionResponse.getCurrencyCode());
    }
    private TransactionDto createTransaction(UUID accountId){
        TransactionDto transactionDto = new TransactionDto();
        // Set transactionDto properties here

        transactionDto.setToAccountId(accountId);
        transactionDto.setCustomerId(UUID.fromString("b40e3e8f-9ec4-4201-a3f8-ce4c188aa4a5"));
        transactionDto.setAmount(BigDecimal.valueOf(3000.00));
        transactionDto.setCurrencyCode("USD");

        return transactionService.createTransaction(transactionDto);
    }
}
