package com.cenoa.history.domain.exception;

import lombok.Getter;

@Getter
public class InvalidStateException extends RuntimeException {

    private final String resourceName;
    private final String resourceUuid;
    private final String state;

    public InvalidStateException(String resourceName, String resourceUuid, String state) {
        super(String.format("%s uuid: %s : '%s'", resourceName, resourceUuid, state));
        this.resourceName = resourceName;
        this.resourceUuid = resourceUuid;
        this.state = state;
    }

}
