package com.everyschool.reportservice.domain.report;

import lombok.Getter;

import java.util.NoSuchElementException;

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
        if (ETC.getCode() == code) {
            return ETC.getText();
        }

        if (VIOLENCE.getCode() == code) {
            return VIOLENCE.getText();
        }

        throw new NoSuchElementException();
    }
}
