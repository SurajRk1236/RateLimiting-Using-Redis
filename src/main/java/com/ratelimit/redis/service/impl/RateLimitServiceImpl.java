package com.ratelimit.redis.service.impl;

import com.ratelimit.redis.config.AppConfig;
import com.ratelimit.redis.service.RateLimitService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RateLimitServiceImpl implements RateLimitService {

    @Autowired
    RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    AppConfig appConfig;


    @Override
    public boolean isAllowed(String key) {
        String redisKey = "rate:" + key;

        return redisTemplate.execute((connection) -> {
            byte[] redisKeyBytes = redisKey.getBytes();

            // Start a transaction
            connection.watch(redisKeyBytes);
            byte[] value = connection.get(redisKeyBytes);
            int currentCount = (value != null) ? Integer.parseInt(new String(value)) : 0;

            connection.multi();

            if (currentCount < appConfig.getMaxRequest()) {
                // Increment the counter if below limit
                connection.incr(redisKeyBytes);
                if (currentCount == 0) {
                    // Set the TTL for the first request
                    connection.expire(redisKeyBytes, appConfig.getAccessTime());
                }
                connection.exec();
                return true; // Allow the request
            } else {
                // Discard changes and deny the request
                connection.discard();
                return false; // Deny the request
            }
        }, true);
    }
}
