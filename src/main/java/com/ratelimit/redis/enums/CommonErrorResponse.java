package com.ratelimit.redis.enums;

import lombok.Getter;

@Getter
public enum CommonErrorResponse {
    RATE_E001("RATE_E001 ", "Rate Limit Exceeded : ");

    final String code;
    final String message;

    CommonErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
