package com.everyschool.boardservice.domain.board;

import lombok.Getter;

@Getter
public enum Category {
    ;

    private final int code;
    private final String text;

    Category(int code, String text) {
        this.code = code;
        this.text = text;
    }
}
