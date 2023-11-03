package com.everyschool.schoolservice.domain.school;

import lombok.Getter;

@Getter
public enum SchoolType {

    ELEMENTARY(2001, "초등학교"),
    MIDDLE(2002, "중학교"),
    HIGH(2003, "고등학교"),
    SPECIAL(2004, "특수학교");

    private final int code;
    private final String text;

    SchoolType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        for (SchoolType schoolType : SchoolType.values()) {
            if (schoolType.getCode() == code) {
                return schoolType.getText();
            }
        }

        throw new IllegalArgumentException("등록이 되지 않은 학교 구분 코드입니다.");
    }
}
