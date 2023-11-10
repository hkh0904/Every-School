package com.everyschool.consultservice.error;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    NO_SUCH_CONSULT("등록이 되지 않은 상담 내역입니다."),
    UNREGISTERED_CONSULT_SCHEDULE("등록이 되지 않은 상담 스케줄입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
