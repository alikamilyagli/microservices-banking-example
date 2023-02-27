package com.cenoa.account.service.impl;

import com.cenoa.account.domain.entity.Account;
import com.cenoa.account.domain.exception.ResourceNotFoundException;
import com.cenoa.account.domain.repository.AccountRepository;
import com.cenoa.account.producer.AccountEventsProducer;
import com.cenoa.account.service.model.command.AccountCreateCommand;
import com.cenoa.account.service.model.command.AccountUpdateCommand;
import com.cenoa.account.service.model.dto.AccountDto;
import com.cenoa.account.service.model.dto.StatusDto;
import com.cenoa.common.model.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountEventsProducer eventsProducer;

    @Before
    public void setUpMockAuthentication() {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(UUID.randomUUID().toString());
    }

    @Test
    public void test_givenValidCommand_whenCreate_thenReturnValidDto() {
        final String currency = "USD";
        final String description = "test description";
        final StatusDto status = StatusDto.ACTIVE;

        AccountCreateCommand command = new AccountCreateCommand();
        command.setCurrency(currency);
        command.setDescription(description);
        command.setStatus(status);

        Mockito.when(accountRepository.save(any(Account.class))).thenAnswer(it -> it.getArgument(0));

        final AccountDto account = accountService.create(command);
        assertEquals(currency, account.getCurrency());
        assertEquals(description, account.getDescription());
        assertEquals(status, account.getStatus());
    }

    @Test(expected = NullPointerException.class)
    public void test_givenInvalidCommand_whenCreate_thenThrowException() {
        final String currency = null;
        final String description = null;
        final StatusDto status = null;

        AccountCreateCommand command = new AccountCreateCommand();
        command.setCurrency(currency);
        command.setDescription(description);
        command.setStatus(status);

        final AccountDto account = accountService.create(command);
    }

    @Test
    public void test_givenValidCommand_whenUpdate_thenReturnValidDto() {
        final String description = "test description";
        final StatusDto status = StatusDto.ACTIVE;

        Account account = new Account();
        account.setId(1L);
        account.setCurrency(Currency.getInstance("USD"));
        account.setStatus(Status.ACTIVE);
        account.setDescription("old descrpition");

        AccountUpdateCommand command = new AccountUpdateCommand();
        command.setDescription(description);
        command.setStatus(status);

        Mockito.when(accountRepository.findByUuid(any(UUID.class))).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(any(Account.class))).thenAnswer(it -> it.getArgument(0));

        final AccountDto updated = accountService.update(UUID.randomUUID(), command);
        assertEquals(description, updated.getDescription());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void test_givenNullUuid_whenUpdate_thenThrowException() {
        AccountUpdateCommand command = new AccountUpdateCommand();
        command.setStatus(StatusDto.ACTIVE);

        accountService.update(null, command);
    }



}
