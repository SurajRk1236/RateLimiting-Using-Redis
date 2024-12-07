package com.ratelimit.redis.controller;

import com.ratelimit.redis.config.AppConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/rate-limit")
public class RateLimitController {

    private final RedisTemplate<String, Integer> redisTemplate;

    private final AppConfig appConfig;

    public RateLimitController(RedisTemplate<String, Integer> redisTemplate, AppConfig appConfig) {
        this.redisTemplate = redisTemplate;
        this.appConfig = appConfig;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus(@RequestParam String userId) {
        String key = "rate:" + userId;
        Integer remaining = redisTemplate.opsForValue().get(key);
        Long ttl = redisTemplate.getExpire(key, TimeUnit.SECONDS);

        Map<String, Object> response = new HashMap<>();
        response.put("remainingRequests", appConfig.getMaxRequest() - remaining);
        response.put("resetTime", ttl);

        return ResponseEntity.ok(response);
    }
}
