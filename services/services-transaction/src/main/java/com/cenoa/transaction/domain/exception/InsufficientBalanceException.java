package com.cenoa.transaction.domain.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class InsufficientBalanceException extends RuntimeException {

    private final UUID accountUuid;

    public InsufficientBalanceException(UUID accountUuid) {
        super(String.format("Account '%s' balance is insufficient", accountUuid));
        this.accountUuid = accountUuid;
    }

}
