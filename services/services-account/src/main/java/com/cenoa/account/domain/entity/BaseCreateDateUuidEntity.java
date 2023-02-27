package com.cenoa.account.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseCreateDateUuidEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NaturalId
    @Column(updatable = false, unique = true)
    protected final UUID uuid = UUID.randomUUID();

    @CreationTimestamp
    private LocalDateTime createDate;

    @CreatedBy
    @Column(updatable = false)
    protected UUID creator;

    @Column
    private boolean deleted;

    private LocalDateTime deletedDate;

}