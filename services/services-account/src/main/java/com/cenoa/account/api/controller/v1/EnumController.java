package com.cenoa.account.api.controller.v1;

import com.cenoa.account.api.response.ApiResponse;
import com.cenoa.account.service.model.dto.StatusDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/enum")
@Tag(name = "Enum API")
@RequiredArgsConstructor
public class EnumController {

    @GetMapping
    @Operation(summary = "Get All Enums")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAll() {
        Map<String, Object> all = new HashMap<>();

        List<StatusDto> statuses = Arrays.asList(StatusDto.values());
        List<Currency> currencies = Arrays.asList(
                Currency.getInstance("TRY"),
                Currency.getInstance("USD"),
                Currency.getInstance("EUR")
        );

        all.put("STATUS", statuses);
        all.put("CURRENCY", currencies);

        return ResponseEntity.ok(new ApiResponse<>(all));
    }
}
