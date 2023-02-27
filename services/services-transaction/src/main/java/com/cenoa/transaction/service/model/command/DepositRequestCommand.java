package com.cenoa.transaction.service.model.command;

import com.cenoa.transaction.service.TransactionCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Deposit Request Command")
public class DepositRequestCommand implements TransactionCommand {

    public UUID accountUuid;
    public BigDecimal amount;

}
