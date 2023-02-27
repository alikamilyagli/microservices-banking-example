package com.cenoa.account.service;

import java.util.UUID;

public interface AccountScopeService {

    void isOwner(UUID accountUuid);

}
