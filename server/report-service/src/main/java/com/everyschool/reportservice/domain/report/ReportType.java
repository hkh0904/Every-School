package com.everyschool.reportservice.domain.report;

import lombok.Getter;

@Getter
public enum ReportType {

    ETC(5000, "기타");

    private final int code;
    private final String text;

    ReportType(int code, String text) {
        this.code = code;
        this.text = text;
    }
}
