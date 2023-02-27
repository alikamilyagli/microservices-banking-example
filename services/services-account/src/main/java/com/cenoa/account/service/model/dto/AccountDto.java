package com.cenoa.account.service.model.dto;

import com.cenoa.account.domain.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

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

    public static AccountDto from(final Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .uuid(account.getUuid())
                .balance(account.getBalance())
                .currency(account.getCurrency().getCurrencyCode())
                .description(account.getDescription())
                .status(StatusDto.valueOf(account.getStatus().name()))
                .build();
    }

}
