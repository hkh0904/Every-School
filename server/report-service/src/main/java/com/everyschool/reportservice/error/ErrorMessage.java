package com.everyschool.reportservice.error;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    UNREGISTERED_PROGRESS_STATUS("등록이 되지 않은 신고 처리 상태입니다."),
    UNREGISTERED_REPORT_TYPE("등록이 되지 않은 신고 유형입니다."),
    NO_SUCH_REPORT("등록이 되지 않은 신고입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
