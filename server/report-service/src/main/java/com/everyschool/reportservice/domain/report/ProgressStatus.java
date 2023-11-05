package com.everyschool.reportservice.domain.report;

import lombok.Getter;

import static com.everyschool.reportservice.error.ErrorMessage.*;

@Getter
public enum ProgressStatus {

    REGISTER(7001, "접수 완료"),
    PROCESS(7002, "처리중"),
    FINISH(7003, "처리 완료");

    private final int code;
    private final String text;

    ProgressStatus(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        for (ProgressStatus status : values()) {
            if (status.getCode() == code) {
                return status.getText();
            }
        }

        throw new IllegalArgumentException(UNREGISTERED_PROGRESS_STATUS.getMessage());
    }
}
