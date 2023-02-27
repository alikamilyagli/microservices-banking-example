package com.cenoa.transaction.service;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionCommand {

    UUID getAccountUuid();
    BigDecimal getAmount();

}
