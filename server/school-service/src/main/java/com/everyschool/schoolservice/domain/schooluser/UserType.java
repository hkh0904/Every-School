package com.everyschool.schoolservice.domain.schooluser;

import lombok.Getter;

@Getter
public enum UserType {

    STUDENT(3001, "학생"),
    PARENT(3002, "학부모"),
    TEACHER(3003, "교직원");

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
