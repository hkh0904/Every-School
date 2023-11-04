package com.everyschool.consultservice.domain.consult;

import lombok.Getter;

@Getter
public enum ConsultType {

    VISIT(4001, "방문상담"),
    CALL(4002, "전화상담");

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

        throw new IllegalArgumentException("등록이 되지 않은 상담 유형 코드입니다.");
    }
}
