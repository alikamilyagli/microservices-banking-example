package com.cenoa.transaction.api.controller.v1;

import com.cenoa.transaction.api.response.ApiResponse;
import com.cenoa.transaction.service.DepositService;
import com.cenoa.transaction.service.WithdrawService;
import com.cenoa.transaction.service.model.command.DepositRequestCommand;
import com.cenoa.transaction.service.model.command.WithdrawRequestCommand;
import com.cenoa.transaction.service.model.dto.TransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Tag(name = "Transaction API")
@RequestMapping("/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final DepositService depositService;
    private final WithdrawService withdrawService;

    @PostMapping("/deposit")
    @Operation(summary = "Deposit request")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<TransactionDto>> deposit(@Valid @RequestBody DepositRequestCommand command) {
        TransactionDto transaction = depositService.request(command);
        return ResponseEntity.ok(new ApiResponse<>(transaction));
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw request")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<TransactionDto>> withdraw(@Valid @RequestBody WithdrawRequestCommand command) {
        TransactionDto transaction = withdrawService.request(command);
        return ResponseEntity.ok(new ApiResponse<>(transaction));
    }


}
