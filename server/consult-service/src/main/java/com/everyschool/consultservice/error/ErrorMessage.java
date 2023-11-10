package com.everyschool.consultservice.error;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    NO_SUCH_CONSULT("등록이 되지 않은 상담 내역입니다."),
    NO_SUCH_CONSULT_SCHEDULE("등록이 되지 않은 상담 스케줄입니다."),
    NOT_REGISTERED_STATUS("등록이 되지 않은 상담 진행 상태 코드입니다."),
    NOT_REGISTERED_TYPE("등록이 되지 않은 상담 유형 코드입니다.");


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
