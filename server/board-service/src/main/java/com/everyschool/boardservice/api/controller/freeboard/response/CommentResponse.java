package com.everyschool.boardservice.api.controller.freeboard.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CommentResponse {

    private int userNumber;
    private String content;
    private String createdDate;
    private List<CommentResponse> reComment;

    @Builder
    public CommentResponse(int userNumber, String content, String createdDate, List<CommentResponse> reComment) {
        this.userNumber = userNumber;
        this.content = content;
        this.createdDate = createdDate;
        this.reComment = reComment;
    }
}
