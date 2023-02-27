package com.cenoa.history.api.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping(produces = { "application/json" })
    public String health(@AuthenticationPrincipal Jwt jwt) {
        return "{\"status\": \"UP\"}";
    }

}
