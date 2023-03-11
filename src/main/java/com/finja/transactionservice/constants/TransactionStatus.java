package com.finja.transactionservice.constants;

public enum TransactionStatus {

    SUCCESS("success"),
    FAILED("failed");
    private final String value;
    TransactionStatus(String accountType){
        this.value = accountType;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
