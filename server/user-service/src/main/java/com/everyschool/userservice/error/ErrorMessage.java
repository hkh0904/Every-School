package com.everyschool.userservice.error;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    NO_SUCH_USER("등록이 되지 않은 회원입니다."),
    UNAUTHORIZED_USER("권한이 없는 사용자입니다."),
    EXPIRE_AUTH_TIME("유효 시간이 만료되었습니다."),
    NOT_EQUAL_AUTH_NUMBER("인증 번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL("이미 사용 중인 이메일입니다."),
    NOT_STUDENT_USER("학생 회원이 아닙니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
