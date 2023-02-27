package com.cenoa.transaction.service.model.dto;

import com.cenoa.transaction.domain.entity.Transaction;
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
public class TransactionDto {

    private Long id;
    private UUID uuid;
    private UUID userUuid;
    private UUID accountUuid;
    private BigDecimal amount;
    private String currency;

    public static TransactionDto from(final Transaction transaction) {
        return TransactionDto.builder()
            .id(transaction.getId())
            .uuid(transaction.getUuid())
            .userUuid(transaction.getUserUuid())
            .accountUuid(transaction.getAccountUuid())
            .amount(transaction.getAmount())
            .currency(transaction.getCurrency().getCurrencyCode())
            .build();
    }

}
