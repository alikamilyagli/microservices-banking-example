package com.cenoa.account.service;

import com.cenoa.account.service.model.command.AccountCreateCommand;
import com.cenoa.account.service.model.command.AccountUpdateCommand;
import com.cenoa.account.service.model.dto.AccountDto;
import com.cenoa.common.event.DepositEvent;
import com.cenoa.common.event.WithdrawEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    List<AccountDto> findAll();
    AccountDto findByUuid(UUID categoryUuid);
    AccountDto create(AccountCreateCommand command);
    AccountDto update(UUID categoryUuid, AccountUpdateCommand command);
    void deleteByUuid(UUID categoryUuid);
    AccountDto updateOnDeposit(DepositEvent event) throws JsonProcessingException;
    AccountDto updateOnWithdraw(WithdrawEvent event) throws JsonProcessingException;

}
