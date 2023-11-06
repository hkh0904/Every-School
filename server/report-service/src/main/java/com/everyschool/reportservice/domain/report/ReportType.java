package com.everyschool.reportservice.domain.report;

import lombok.Getter;

import java.util.NoSuchElementException;

import static com.everyschool.reportservice.error.ErrorMessage.UNREGISTERED_REPORT_TYPE;

@Getter
public enum ReportType {

    ETC(5000, "기타"),
    VIOLENCE(5001, "학교폭력");

    private final int code;
    private final String text;

    ReportType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        for (ReportType type : values()) {
            if (type.getCode() == code) {
                return type.getText();
            }
        }

        throw new NoSuchElementException(UNREGISTERED_REPORT_TYPE.getMessage());
    }
}
