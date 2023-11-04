package com.everyschool.schoolservice.api;

import lombok.Getter;

import java.util.List;

@Getter
public class Result<T> {

    private final int count;
    private final List<T> content;

    private Result(List<T> content) {
        this.count = content.size();
        this.content = content;
    }

    public static <T> Result<T> of(List<T> content) {
        return new Result<>(content);
    }
}
