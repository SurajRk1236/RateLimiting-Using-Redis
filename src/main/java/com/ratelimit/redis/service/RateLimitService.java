package com.ratelimit.redis.service;

public interface RateLimitService {
    boolean isAllowed(String key);
}
