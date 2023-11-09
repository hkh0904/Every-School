package com.everyschool.schoolservice.domain.schooluser;

import lombok.Getter;

@Getter
public enum UserType {

    STUDENT(3001, "학생"),
    FATHER(3002, "아버님"),
    MOTHER(3003, "어머님"),
    TEACHER(3004, "교직원");

    private final int code;
    private final String text;

    UserType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        for (UserType userType : UserType.values()) {
            if (userType.getCode() == code) {
                return userType.getText();
            }
        }

        throw new IllegalArgumentException();
    }
}
