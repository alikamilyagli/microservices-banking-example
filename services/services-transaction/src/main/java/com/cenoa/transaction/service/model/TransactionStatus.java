package com.cenoa.transaction.service.model;

import lombok.Getter;

@Getter
public enum TransactionStatus {

    PENDING,
    COMPLETED,
    FAILED;

}
