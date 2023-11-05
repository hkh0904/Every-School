package com.everyschool.schoolservice;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    NOT_EXIST_MY_SCHOOL_CLASS("담당을 하는 학급이 존재하지 않습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
