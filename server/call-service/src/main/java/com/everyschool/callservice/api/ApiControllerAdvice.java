package com.everyschool.callservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * Api 공통 예외 처리 컨트롤러
 *
 * @author 임우택
 */
@RestControllerAdvice
public class ApiControllerAdvice {

    /**
     * BindException 공통 처리 메서드
     * @param e BindException
     * @return 400 오류 메세지
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e) {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
            null
        );
    }

    /**
     * IllegalArgumentException 공통 처리 메서드
     * @param e IllegalArgumentException
     * @return 400 오류 메세지
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Object> illegalArgumentException(IllegalArgumentException e) {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            e.getMessage(),
            null
        );
    }

    /**
     * NoSuchElementException 공통 처리 메서드
     * @param e NoSuchElementException
     * @return 400 오류 메세지
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponse<Object> noSuchElementException(NoSuchElementException e) {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            e.getMessage(),
            null
        );
    }
}
