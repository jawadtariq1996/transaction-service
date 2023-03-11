package com.finja.transactionservice.constants;

public enum TransactionType {
    ATM("ATM"),
    ONLINE("Online");
    private final String value;
    TransactionType(String accountType){
        this.value = accountType;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
