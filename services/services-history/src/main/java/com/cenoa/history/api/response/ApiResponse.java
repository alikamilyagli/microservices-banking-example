package com.cenoa.history.api.response;

import lombok.Data;

import java.time.Instant;

@Data
public class ApiResponse<T> {

    private T data;
    private T meta;

    private long timestamp = Instant.now().toEpochMilli();

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(T data, T meta) {
        this.data = data;
        this.meta = meta;
    }

}
