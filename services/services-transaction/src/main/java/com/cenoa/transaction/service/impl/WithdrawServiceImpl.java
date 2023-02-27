package com.cenoa.transaction.service.impl;

import com.cenoa.common.event.EventType;
import com.cenoa.common.event.WithdrawEvent;
import com.cenoa.transaction.domain.entity.Transaction;
import com.cenoa.transaction.domain.exception.InsufficientBalanceException;
import com.cenoa.transaction.domain.exception.InvalidStateException;
import com.cenoa.transaction.domain.model.dto.AccountDto;
import com.cenoa.transaction.domain.model.dto.StatusDto;
import com.cenoa.transaction.domain.repository.TransactionRepository;
import com.cenoa.transaction.producer.WithdrawEventsProducer;
import com.cenoa.transaction.service.BaseTransactionService;
import com.cenoa.transaction.service.WithdrawService;
import com.cenoa.transaction.service.model.command.WithdrawRequestCommand;
import com.cenoa.transaction.service.model.dto.TransactionDto;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
@RequiredArgsConstructor
public class WithdrawServiceImpl extends BaseTransactionService implements WithdrawService {

    private static final BigDecimal INVERSE_FACTOR = new BigDecimal(-1);

    private final TransactionRepository transactionRepository;
    private final WithdrawEventsProducer eventsProducer;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    @Override
    public TransactionDto request(final WithdrawRequestCommand command) {
        AccountDto account = getAccount(command.getAccountUuid());

        if (account.getStatus().equals(StatusDto.INACTIVE)) {
            throw new InvalidStateException("Account", account.getUuid(), StatusDto.INACTIVE.name());
        }

        if (account.getBalance().compareTo(command.getAmount()) < 0) {
            throw new InsufficientBalanceException(command.getAccountUuid());
        }

        Transaction transaction = new Transaction();
        transaction.setUserUuid(getAuthenticatedUserUuid());
        transaction.setAccountUuid(command.getAccountUuid());
        transaction.setAmount(command.getAmount().multiply(INVERSE_FACTOR));
        transaction.setCurrency(Currency.getInstance(account.getCurrency()));
        transaction = transactionRepository.save(transaction);
        TransactionDto transactionDto = TransactionDto.from(transaction);

        WithdrawEvent event = new WithdrawEvent(gson.toJson(transactionDto), EventType.CREATE);
        eventsProducer.sendMessage(event);

        return transactionDto;
    }

}
