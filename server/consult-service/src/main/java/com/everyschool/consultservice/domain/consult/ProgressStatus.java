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
        for (ProgressStatus status : values()) {
            if (status.getCode() == code) {
                return status.getText();
            }
        }

        throw new IllegalArgumentException("등록이 되지 않은 상담 진행 상태 코드입니다.");
    }
}
