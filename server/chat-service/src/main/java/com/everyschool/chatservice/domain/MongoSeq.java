package com.everyschool.chatservice.domain;

public class MongoSeq {

    private static Long seq = 0L;

    public static Long getSeq() {
        return ++seq;
    }
}
