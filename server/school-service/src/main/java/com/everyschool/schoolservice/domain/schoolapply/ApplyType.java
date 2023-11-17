package com.everyschool.schoolservice.domain.schoolapply;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public enum ApplyType {

    STUDENT(6001, "학생 신청"),
    FATHER(6002, "아버님 신청"),
    MOTHER(6003, "어머님 신청");

    private final int code;
    private final String text;

    ApplyType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        for (ApplyType type : values()) {
            if (type.getCode() == code) {
                return type.getText();
            }
        }
        throw new NoSuchElementException();
    }
}
