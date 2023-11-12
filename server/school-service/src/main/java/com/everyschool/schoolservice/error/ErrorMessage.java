package com.everyschool.schoolservice.error;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    NO_SUCH_SCHOOL("등록이 되지 않은 학교입니다."),
    NOT_EXIST_MY_SCHOOL_CLASS("담당을 하는 학급이 존재하지 않습니다."),
    NO_SUCH_SCHOOL_CLASS("등록이 되지 않은 학급입니다."),
    UNREGISTERED_SCHOOL_APPLY("등록이 되지 않은 신청입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
