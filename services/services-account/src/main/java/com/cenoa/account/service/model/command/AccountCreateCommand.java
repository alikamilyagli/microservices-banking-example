package com.cenoa.account.service.model.command;

import com.cenoa.account.service.model.dto.StatusDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Create Account Command")
public class AccountCreateCommand {

    @NotEmpty(message = "currency cannot be empty")
    @Schema(name = "Currency", allowableValues = "NonNull String")
    private String currency;

    @NotEmpty(message = "status cannot be empty")
    @Schema(name = "Status", allowableValues = "NonNull String")
    private StatusDto status;

    private String description;

}
