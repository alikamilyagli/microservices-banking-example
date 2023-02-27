package com.cenoa.transaction.service.impl;

import com.cenoa.common.event.DepositEvent;
import com.cenoa.common.event.EventType;
import com.cenoa.transaction.domain.entity.Transaction;
import com.cenoa.transaction.domain.exception.InvalidStateException;
import com.cenoa.transaction.domain.model.dto.AccountDto;
import com.cenoa.transaction.domain.model.dto.StatusDto;
import com.cenoa.transaction.domain.repository.TransactionRepository;
import com.cenoa.transaction.producer.DepositEventsProducer;
import com.cenoa.transaction.service.BaseTransactionService;
import com.cenoa.transaction.service.DepositService;
import com.cenoa.transaction.service.model.command.DepositRequestCommand;
import com.cenoa.transaction.service.model.dto.TransactionDto;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl extends BaseTransactionService implements DepositService {

    private final TransactionRepository transactionRepository;
    private final DepositEventsProducer eventsProducer;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    @Override
    public TransactionDto request(final DepositRequestCommand command) {
        AccountDto account = getAccount(command.getAccountUuid());

        if (account.getStatus().equals(StatusDto.INACTIVE)) {
            throw new InvalidStateException("Account", account.getUuid(), StatusDto.INACTIVE.name());
        }

        Transaction transaction = new Transaction();
        transaction.setUserUuid(getAuthenticatedUserUuid());
        transaction.setAccountUuid(command.getAccountUuid());
        transaction.setAmount(command.getAmount());
        transaction.setCurrency(Currency.getInstance(account.getCurrency()));
        transaction = transactionRepository.save(transaction);
        TransactionDto transactionDto = TransactionDto.from(transaction);

        DepositEvent event = new DepositEvent(gson.toJson(transactionDto), EventType.CREATE);
        eventsProducer.sendMessage(event);

        return transactionDto;
    }







}
