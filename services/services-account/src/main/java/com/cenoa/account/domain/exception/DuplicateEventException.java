package com.cenoa.account.domain.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DuplicateEventException extends RuntimeException {

    private final UUID eventUuid;

    public DuplicateEventException(UUID eventUuid) {
        super(String.format("Duplicate event : '%s'", eventUuid));
        this.eventUuid = eventUuid;
    }

}
