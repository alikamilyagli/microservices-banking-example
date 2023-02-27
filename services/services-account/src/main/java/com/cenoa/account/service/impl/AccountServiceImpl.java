package com.cenoa.account.service.impl;

import com.cenoa.account.domain.entity.Account;
import com.cenoa.account.domain.entity.ProcessedEvent;
import com.cenoa.account.domain.exception.DuplicateEventException;
import com.cenoa.account.domain.exception.ResourceNotFoundException;
import com.cenoa.account.domain.repository.AccountRepository;
import com.cenoa.account.domain.repository.ProcessedEventRepository;
import com.cenoa.account.producer.AccountEventsProducer;
import com.cenoa.account.service.AccountService;
import com.cenoa.account.service.model.command.AccountCreateCommand;
import com.cenoa.account.service.model.command.AccountUpdateCommand;
import com.cenoa.account.service.model.dto.AccountDto;
import com.cenoa.common.event.AccountEvent;
import com.cenoa.common.event.DepositEvent;
import com.cenoa.common.event.EventType;
import com.cenoa.common.event.WithdrawEvent;
import com.cenoa.common.model.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ProcessedEventRepository processedEventRepository;
    private final AccountEventsProducer eventsProducer;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    @Override
    public List<AccountDto> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return accountRepository.findAllByDeletedIsFalseAndUserUuid(getAuthenticatedUserUuid(), sort)
                .stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findByUuid(final UUID accountUuid) {
        Account account = getAccount(accountUuid);
        return AccountDto.from(account);
    }

    @Override
    @Transactional
    public AccountDto create(final AccountCreateCommand command) {
        Account account = new Account();
        buildAccount(account, command);
        account = accountRepository.save(account);
        AccountDto accountDto = AccountDto.from(account);

        AccountEvent event = new AccountEvent(gson.toJson(accountDto), EventType.CREATE);
        eventsProducer.sendMessage(event);

        return accountDto;
    }

    @Override
    public AccountDto update(final UUID accountUuid, final AccountUpdateCommand accountDto) {
        Account account = getAccount(accountUuid);
        buildAccount(account, accountDto);
        account = accountRepository.save(account);

        AccountEvent event = new AccountEvent(gson.toJson(accountDto), EventType.UPDATE);
        eventsProducer.sendMessage(event);

        return AccountDto.from(account);
    }

    @Override
    public void deleteByUuid(final UUID accountUuid) {
        Account account = getAccount(accountUuid);
        account.setDeleted(true);
        account.setDeletedDate(LocalDateTime.now());
        accountRepository.save(account);

        AccountEvent event = new AccountEvent(gson.toJson(AccountDto.from(account)), EventType.DELETE);
        eventsProducer.sendMessage(event);
    }

    @Override
    public AccountDto updateOnDeposit(final DepositEvent event) throws JsonProcessingException {
        deduplicate(event.getUuid());
        JsonNode transactionJson = mapper.readTree(event.getItem().toString());
        UUID accountUuid = UUID.fromString(String.valueOf(transactionJson.get("accountUuid")).replace("\"", ""));
        BigDecimal amount = new BigDecimal(String.valueOf(transactionJson.get("amount")).replace("\"", ""));
        Account account = getAccount(accountUuid);
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        account = accountRepository.save(account);
        return AccountDto.from(account);
    }

    @Override
    public AccountDto updateOnWithdraw(final WithdrawEvent event) throws JsonProcessingException {
        deduplicate(event.getUuid());
        JsonNode transactionJson = mapper.readTree(event.getItem().toString());
        UUID accountUuid = UUID.fromString(String.valueOf(transactionJson.get("accountUuid")).replace("\"", ""));
        BigDecimal amount = new BigDecimal(String.valueOf(transactionJson.get("amount")).replace("\"", ""));
        Account account = getAccount(accountUuid);
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        account = accountRepository.save(account);
        return AccountDto.from(account);
    }


    private Account getAccount(final UUID accountUuid) {
        return accountRepository.findByUuid(accountUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "uuid", accountUuid));
    }

    private Account buildAccount(Account account, final AccountCreateCommand command) {
        account.setUserUuid(getAuthenticatedUserUuid());
        account.setCurrency(Currency.getInstance(command.getCurrency()));
        account.setDescription(command.getDescription());
        account.setStatus(Status.valueOf(command.getStatus().name()));
        account.setCreateDate(LocalDateTime.now());
        account.setCreator(getAuthenticatedUserUuid());
        return account;
    }

    private Account buildAccount(Account account, final AccountUpdateCommand command) {
        account.setDescription(command.getDescription());
        account.setStatus(Status.valueOf(command.getStatus().name()));
        account.setUpdateDate(LocalDateTime.now());
        account.setModifier(getAuthenticatedUserUuid());
        return account;
    }

    private UUID getAuthenticatedUserUuid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(authentication.getName());
    }

    private void deduplicate(final UUID eventUuid) throws DuplicateEventException {
        try {
            processedEventRepository.saveAndFlush(new ProcessedEvent(eventUuid));
            log.debug("Event persisted with uuid: {}", eventUuid);
        } catch (DataIntegrityViolationException e) {
            log.warn("Event already processed: {}", eventUuid);
            throw new DuplicateEventException(eventUuid);
        }
    }

}
