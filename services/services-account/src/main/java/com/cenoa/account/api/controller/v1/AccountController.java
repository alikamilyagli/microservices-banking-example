package com.cenoa.account.api.controller.v1;

import com.cenoa.account.api.response.ApiResponse;
import com.cenoa.account.service.AccountScopeService;
import com.cenoa.account.service.model.command.AccountCreateCommand;
import com.cenoa.account.service.model.command.AccountUpdateCommand;
import com.cenoa.account.service.model.dto.AccountDto;
import com.cenoa.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Account API")
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountScopeService accountScopeService;

    @GetMapping
    @Operation(summary = "Returns all user accounts")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<List<AccountDto>>> getAccounts() {
        List<AccountDto> accounts = accountService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(accounts));
    }

    @GetMapping("/{accountUuid}")
    @Operation(summary = "Return account details")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<AccountDto>> getAccount(@PathVariable UUID accountUuid) {
        accountScopeService.isOwner(accountUuid);
        AccountDto account = accountService.findByUuid(accountUuid);
        return ResponseEntity.ok(new ApiResponse<>(account));
    }

    @PostMapping
    @Operation(summary = "Create an account")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<AccountDto>> createAccount(@Valid @RequestBody AccountCreateCommand command) {
        AccountDto account = accountService.create(command);
        return ResponseEntity.ok(new ApiResponse<>(account));
    }

    @PutMapping("/{accountUuid}")
    @Operation(summary = "Update an account")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<AccountDto>> updateAccount(@PathVariable UUID accountUuid,
                                                                 @Valid @RequestBody AccountUpdateCommand command) {
        accountScopeService.isOwner(accountUuid);
        AccountDto account = accountService.update(accountUuid, command);
        return ResponseEntity.ok(new ApiResponse<>(account));
    }

    @DeleteMapping("{accountUuid}")
    @Operation(summary = "Delete an account")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<AccountDto>> delete(@PathVariable UUID accountUuid) {
        accountScopeService.isOwner(accountUuid);
        accountService.deleteByUuid(accountUuid);
        return ResponseEntity.noContent().build();
    }


}
