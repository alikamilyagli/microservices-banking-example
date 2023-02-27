package com.cenoa.common.event;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositEvent {

    private UUID uuid;
    @NotNull
    @Valid
    private Object item;
    private EventType eventType;

    public DepositEvent(Object item, EventType eventType) {
        this.uuid = UUID.randomUUID();
        this.item = item;
        this.eventType = eventType;
    }
}
