package com.everyschool.userservice.domain.user;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public enum UserType {

    MASTER(1000, "마스터"),
    STUDENT(1001, "학생"),
    PARENT(1002, "학부모"),
    TEACHER(1003, "교직원");

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

        throw new NoSuchElementException();
    }
}
