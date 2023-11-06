package com.everyschool.userservice.error;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    UNREGISTERED_USER("등록이 되지 않은 회원입니다."),
    UNAUTHORIZED_USER("권한이 없는 사용자입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
