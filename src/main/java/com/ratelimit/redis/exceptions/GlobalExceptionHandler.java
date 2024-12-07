package com.ratelimit.redis.exceptions;

import com.ratelimit.redis.enums.CommonErrorResponse;
import com.ratelimit.redis.responses.CommonResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static com.ratelimit.redis.constants.CommonConstants.DELIMITER;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResponse<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return getErrorResponse(e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(DELIMITER)));
    }

    @ExceptionHandler(GenericException.class)
    @ResponseBody
    public CommonResponse<?> handleException(GenericException exception) {
        return getErrorResponse(exception);
    }

    @ExceptionHandler(RateLimitExceededException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ResponseBody
    public CommonResponse<?> handleException(RateLimitExceededException exception) {
        CommonResponse<?> errorResponse = getErrorResponse(CommonErrorResponse.RATE_E001.getCode() + exception.getMessage());
        errorResponse.setCode(String.valueOf(HttpStatus.TOO_MANY_REQUESTS.value()));
        return errorResponse;
    }


    private CommonResponse<?> getErrorResponse(GenericException exception) {
        return CommonResponse.builder()
                .errorMessage(exception.getErrorResponse().getMessage())
                .code(exception.getErrorResponse().getCode()).build();
    }

    private CommonResponse<?> getErrorResponse(String message) {
        return CommonResponse.builder()
                .errorMessage(message).build();
    }
}
