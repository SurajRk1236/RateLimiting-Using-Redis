package com.ratelimit.redis.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppConfig {


    @Value("${ratelimit.maxRequest}")
    int maxRequest;

    @Value("${ratelimit.ttl}")
    long accessTime;

}
