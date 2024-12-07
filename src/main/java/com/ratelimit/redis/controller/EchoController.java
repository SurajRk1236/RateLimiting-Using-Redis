package com.ratelimit.redis.controller;

import com.ratelimit.redis.responses.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ratelimit.redis.constants.SuccessMessageConstants.ECHO_HEALTHY;


@RestController
@RequestMapping("/echo")
public class EchoController {

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<String> getWeatherByCity() {
        return CommonResponse.<String>builder().data(ECHO_HEALTHY).build();
    }
}
