package com.cenoa.transaction.service;

import com.cenoa.transaction.domain.exception.ResourceNotFoundException;
import com.cenoa.transaction.domain.model.dto.AccountDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public abstract class BaseTransactionService {

    private static final String ACCOUNT_SERVICE_URL = "http://localhost:8001";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected String getAuthenticatedUserAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt token = ((JwtAuthenticationToken) authentication).getToken();
        return token.getTokenValue();
    }

    protected UUID getAuthenticatedUserUuid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(authentication.getName());
    }

    protected AccountDto getAccount(final UUID accountUuid) {
        try {
            StringBuilder uriBuilder = new StringBuilder();
            uriBuilder.append(ACCOUNT_SERVICE_URL)
                .append("/v1")
                .append("/account/")
                .append(accountUuid);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + getAuthenticatedUserAccessToken());
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<JsonNode> response = restTemplate.exchange(
                uriBuilder.toString(), HttpMethod.GET, requestEntity, JsonNode.class);

            return objectMapper.readValue(
                response.getBody().get("data").toString(),
                new TypeReference<AccountDto>() {});

        } catch (Exception e) {
            throw new ResourceNotFoundException("Account", "uuid", accountUuid);
        }
    }

}
