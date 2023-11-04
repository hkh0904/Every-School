package com.everyschool.consultservice.domain.consult;

import lombok.Getter;

@Getter
public enum ProgressStatus {

    WAIT(5001, "승인 대기중"),
    RESERVATION(5002, "예약 완료"),
    FINISH(5003, "상담 완료"),
    REJECT(5004, "상담 불가");

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

        throw new IllegalArgumentException("등록이 되지 않은 상담 진행 상태 코드입니다.");
    }
}
