package com.everyschool.alarmservice.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * API Response 공통 스팩
 *
 * @author 임우택
 * @param <T> 반환할 데이터의 타입
 */
@Getter
public class ApiResponse<T> {

    private final int code;
    private final HttpStatus status;
    private final String message;
    private final T data;

    private ApiResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(OK, "SUCCESS", data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return of(CREATED, "CREATED", data);
    }
}
