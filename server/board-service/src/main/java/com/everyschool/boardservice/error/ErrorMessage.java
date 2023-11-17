package com.everyschool.boardservice.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    NO_SUCH_BOARD("등록되지 않은 게시물입니다."),
    NO_SUCH_COMMENT("등록되지 않은 댓글입니다.");

    private final String message;
}
