package com.everyschool.reportservice.domain.report;

import lombok.Getter;

@Getter
public enum ProgressStatus {

    REGISTER(4001, "접수 완료"),
    PROCESS(4002, "처리중"),
    FINISH(4003, "처리 완료");

    private final int code;
    private final String text;

    ProgressStatus(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        if (code == 4001) {
            return REGISTER.getText();
        }

        if (code == 4002) {
            return PROCESS.getText();
        }

        if (code == 4003) {
            return FINISH.getText();
        }

        throw new IllegalArgumentException();
    }
}
