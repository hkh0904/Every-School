package com.everyschool.chatservice.domain.chat;

import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public enum ChatStatus {

    PLANE(8001, "평범"),
    WARNING(8002, "악성의심"),
    BAD(8003, "악성");

    private final int code;
    private final String text;

    ChatStatus(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static String getText(int code) {
        for (ChatStatus chatStatus : ChatStatus.values()) {
            if (chatStatus.getCode() == code) {
                return chatStatus.getText();
            }
        }

        throw new NoSuchElementException();
    }
}
