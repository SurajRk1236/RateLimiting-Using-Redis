package com.ratelimit.redis.controller;

import com.ratelimit.redis.responses.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ratelimit.redis.constants.SuccessMessageConstants.PROTECTED_ENDPOINT_WITH_BEARER_TOKEN;


@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/protected")
    public CommonResponse<String> protectedEndpoint() {
        return CommonResponse.<String>builder().data(PROTECTED_ENDPOINT_WITH_BEARER_TOKEN).build();
    }
}
