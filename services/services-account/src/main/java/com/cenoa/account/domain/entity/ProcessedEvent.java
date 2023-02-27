package com.cenoa.account.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name="ProcessedEvent")
public class ProcessedEvent implements Serializable, Persistable<UUID> {

    @Id
    private UUID eventUuid;

    @Transient
    @Override
    public UUID getId() {
        return eventUuid;
    }

    /**
     * Ensures Hibernate always does an INSERT operation when save() is called.
     */
    @Transient
    @Override
    public boolean isNew() {
        return true;
    }
}
