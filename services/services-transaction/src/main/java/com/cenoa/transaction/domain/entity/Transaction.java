package com.cenoa.transaction.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "transaction_gen", sequenceName = "transaction_id_seq", allocationSize = 1)
@Audited
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_gen")
    private Long id;
    private UUID userUuid;
    private UUID accountUuid;
    private BigDecimal amount;
    private Currency currency;

}
