package com.example.cache_test.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommonResponse<T>(T result, PageInfo page) {

    public CommonResponse(T result) {
        this(result, null);
    }

}
