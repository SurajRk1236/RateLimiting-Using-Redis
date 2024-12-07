package com.ratelimit.redis.config;

import com.ratelimit.redis.enums.CommonErrorResponse;
import com.ratelimit.redis.exceptions.RateLimitExceededException;
import com.ratelimit.redis.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class RateLimitFilter extends OncePerRequestFilter {

    @Autowired
    RateLimitService rateLimitService;

    @Qualifier("handlerExceptionResolver")
    @Autowired
    HandlerExceptionResolver resolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String clientId = request.getRemoteAddr();
            String path = request.getRequestURI();
            if ("/rate-limit/status".equals(path)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (!rateLimitService.isAllowed(clientId)) {
                throw new RateLimitExceededException(CommonErrorResponse.RATE_E001.getMessage() + clientId);
            }

            filterChain.doFilter(request, response);
        } catch (RateLimitExceededException e) {
            resolver.resolveException(request, response, null, e);
        }
    }
}
