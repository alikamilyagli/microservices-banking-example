package com.cenoa.transaction.service;

import com.cenoa.transaction.service.model.command.DepositRequestCommand;
import com.cenoa.transaction.service.model.dto.TransactionDto;

public interface DepositService {

    TransactionDto request(DepositRequestCommand command);

}
