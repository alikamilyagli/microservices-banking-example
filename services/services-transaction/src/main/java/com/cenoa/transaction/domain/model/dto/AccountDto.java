package com.cenoa.transaction.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

//FIXME: move under to common package
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    private Long id;
    private UUID uuid;
    private BigDecimal balance;
    private String currency;
    private String description;
    private StatusDto status;


}
