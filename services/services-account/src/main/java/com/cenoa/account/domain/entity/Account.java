package com.cenoa.account.domain.entity;

import com.cenoa.common.model.Status;
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
@SequenceGenerator(name = "account_gen", sequenceName = "account_id_seq", allocationSize = 1)
@Audited
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_gen")
    private Long id;
    private UUID userUuid;
    private BigDecimal balance = BigDecimal.ZERO;
    private Currency currency;
    private String description;
    private Status status;

}
