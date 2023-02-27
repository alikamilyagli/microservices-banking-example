package com.cenoa.transaction.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity extends BaseCreateDateUuidEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    @UpdateTimestamp
    @Column
    private LocalDateTime updateDate;

    @LastModifiedBy
    protected UUID modifier;

    @Version
    private long version;

}
