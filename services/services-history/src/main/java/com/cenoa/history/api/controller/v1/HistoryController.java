package com.cenoa.history.api.controller.v1;

import com.cenoa.common.event.AccountEvent;
import com.cenoa.common.event.EventType;
import com.cenoa.history.api.response.ApiResponse;
import com.cenoa.history.service.HistoryService;
import com.cenoa.history.service.model.HistoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "History API")
@RequestMapping("/v1/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    @Operation(summary = "Returns all history")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<List<HistoryDto>>> getHistory() {
        List<HistoryDto> history = historyService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(history));
    }

    @PostMapping
    @Operation(summary = "Save history")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponse<HistoryDto>> saveHistory() {
        AccountEvent accountEvent = new AccountEvent("zuhaha", EventType.CREATE);
        HistoryDto history = historyService.save(accountEvent);
        return ResponseEntity.ok(new ApiResponse<>(history));
    }


}
