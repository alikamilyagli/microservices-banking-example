package com.cenoa.account.service.impl;

import com.cenoa.account.domain.entity.Account;
import com.cenoa.account.domain.exception.AuthorizationException;
import com.cenoa.account.domain.exception.ResourceNotFoundException;
import com.cenoa.account.domain.repository.AccountRepository;
import com.cenoa.account.service.AccountScopeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountScopeServiceImpl implements AccountScopeService {

    private final AccountRepository accountRepository;

    @Override
    public void isOwner(final UUID accountUuid) {
        Account account = getAccount(accountUuid);
        if (!account.getUserUuid().equals(getAuthenticatedUserUuid())) {
            throw new AuthorizationException("Account", accountUuid, getAuthenticatedUserUuid());
        }
    }

    private Account getAccount(final UUID accountUuid) {
        return accountRepository.findByUuid(accountUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "uuid", accountUuid));
    }

    private UUID getAuthenticatedUserUuid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(authentication.getName());
    }

}
