package com.cenoa.transaction.domain.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AuthorizationException extends RuntimeException {

    private final String resourceName;
    private final UUID resourceUuid;
    private final UUID userUuid;

    public AuthorizationException(String resourceName, UUID resourceUuid, UUID userUuid) {
        super(String.format("%s user does not own %s : '%s'", userUuid, resourceName, resourceUuid));
        this.resourceName = resourceName;
        this.resourceUuid = resourceUuid;
        this.userUuid = userUuid;
    }

}
