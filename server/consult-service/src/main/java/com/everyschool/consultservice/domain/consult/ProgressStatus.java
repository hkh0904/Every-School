package com.everyschool.consultservice.domain.consult;

import lombok.Getter;

@Getter
public enum ProgressStatus {

    WAIT(3001, "승인 대기중"),
    RESERVATION(3002, "예약 완료"),
    FINISH(3003, "상담 완료"),
    REJECT(3004, "상담 불가");

    private final int code;
    private final String text;

    ProgressStatus(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        if (WAIT.getCode() == code) {
            return WAIT.getText();
        }

        if (RESERVATION.getCode() == code) {
            return RESERVATION.getText();
        }

        if (FINISH.getCode() == code) {
            return FINISH.getText();
        }

        if (REJECT.getCode() == code) {
            return REJECT.getText();
        }

        throw new IllegalArgumentException();
    }
}
