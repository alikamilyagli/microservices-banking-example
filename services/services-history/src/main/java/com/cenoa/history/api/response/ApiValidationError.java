package com.cenoa.history.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiValidationError {

    private String field;
    private String message;

}
