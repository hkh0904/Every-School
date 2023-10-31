package com.everyschool.reportservice.domain.report;

import lombok.Getter;

@Getter
public enum ProgressStatus {

    Register(4001, "접수 완료"),
    Process(4002, "처리중"),
    finish(4003, "처리 완료");

    private final int code;
    private final String text;

    ProgressStatus(int code, String text) {
        this.code = code;
        this.text = text;
    }
}
