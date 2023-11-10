package com.everyschool.boardservice.api.app.controller.board.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CreateCommentRequest {

    @NotEmpty(message = "댓글 내용을 필수입니다.")
    private String content;

    @Builder
    private CreateCommentRequest(String content) {
        this.content = content;
    }
}
