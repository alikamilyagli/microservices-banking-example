package com.cenoa.transaction.service;

import com.cenoa.transaction.service.model.command.WithdrawRequestCommand;
import com.cenoa.transaction.service.model.dto.TransactionDto;

public interface WithdrawService {

    TransactionDto request(WithdrawRequestCommand command);

}
