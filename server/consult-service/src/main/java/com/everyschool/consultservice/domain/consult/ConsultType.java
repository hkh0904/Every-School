package com.everyschool.consultservice.domain.consult;

import lombok.Getter;

import static com.everyschool.consultservice.error.ErrorMessage.NOT_REGISTERED_TYPE;

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
        for (ConsultType type : values()) {
            if (type.getCode() == code) {
                return type.getText();
            }
        }

        throw new IllegalArgumentException(NOT_REGISTERED_TYPE.getMessage());
    }
}
