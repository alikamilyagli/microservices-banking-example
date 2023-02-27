package com.cenoa.transaction.domain.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class InvalidStateException extends RuntimeException {

    private final String resourceName;
    private final UUID resourceUuid;
    private final String state;

    public InvalidStateException(String resourceName, UUID resourceUuid, String state) {
        super(String.format("%s uuid: %s : '%s'", resourceName, resourceUuid, state));
        this.resourceName = resourceName;
        this.resourceUuid = resourceUuid;
        this.state = state;
    }

}
