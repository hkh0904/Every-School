package com.everyschool.boardservice.api;

import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public class SliceResponse<T> {

    private final List<T> content;
    private final int currentPage;
    private final int size;
    private final Boolean isFirst;
    private final Boolean isLast;

    public SliceResponse(Slice<T> data) {
        this.content = data.getContent();
        this.currentPage = data.getNumber() + 1;
        this.size = data.getSize();
        this.isFirst = data.isFirst();
        this.isLast = data.isLast();
    }
}
