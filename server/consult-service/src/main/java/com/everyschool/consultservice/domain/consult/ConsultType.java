package com.everyschool.consultservice.domain.consult;

import lombok.Getter;

@Getter
public enum ConsultType {

    VISIT(2001, "방문상담"),
    CALL(2002, "전화상담");

    private final int code;
    private final String text;

    ConsultType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        if (VISIT.getCode() == code) {
            return VISIT.getText();
        }

        if (CALL.getCode() == code) {
            return CALL.getText();
        }

        throw new IllegalArgumentException();
    }
}
