package com.finja.transactionservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "finja_transaction")
public class Transaction {

    @Id
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID transactionId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "from_account_id", nullable = false)
    private UUID fromAccountId;

    @Column(name = "to_account_id", nullable = false)
    private UUID toAccountId;

    @Column(name = "transaction_type",nullable = false)
    private String transactionType;

    @Column(name = "transaction_status",nullable = false)
    private String transactionStatus;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @Column(name = "currency",nullable = false)
    private String currency;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;
}
