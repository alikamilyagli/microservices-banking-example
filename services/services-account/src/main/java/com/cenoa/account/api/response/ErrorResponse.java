package com.cenoa.account.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    Long timestamp;
    String message;
    ArrayList<ApiValidationError> validationMessages;
}