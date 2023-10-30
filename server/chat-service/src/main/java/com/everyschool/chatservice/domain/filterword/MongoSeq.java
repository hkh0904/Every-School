package com.everyschool.chatservice.domain.filterword;

public class MongoSeq {

    private static Long seq = 0L;

    public static Long getSeq() {
        return ++seq;
    }
}
