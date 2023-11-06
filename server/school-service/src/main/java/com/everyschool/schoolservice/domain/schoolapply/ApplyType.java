package com.everyschool.schoolservice.domain.schoolapply;

import lombok.Getter;

@Getter
public enum ApplyType {

    STUDENT(6001, "학생신청"),
    PARENT(6002, "학부모신청");

    private final int code;
    private final String text;

    ApplyType(int code, String text) {
        this.code = code;
        this.text = text;
    }
}
