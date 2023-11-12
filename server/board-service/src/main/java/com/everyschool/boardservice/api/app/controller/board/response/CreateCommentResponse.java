package com.everyschool.boardservice.api.app.controller.board.response;

import com.everyschool.boardservice.domain.board.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCommentResponse {

    private Long commentId;
    private Long parentCommentId;
    private String content;
    private int depth;
    private LocalDateTime createdDate;

    @Builder
    public CreateCommentResponse(Long commentId, Long parentCommentId, String content, int depth, LocalDateTime createdDate) {
        this.commentId = commentId;
        this.parentCommentId = parentCommentId;
        this.content = content;
        this.depth = depth;
        this.createdDate = createdDate;
    }

    public static CreateCommentResponse of(Comment comment) {
        return CreateCommentResponse.builder()
            .commentId(comment.getId())
            .parentCommentId(comment.getParent().getId())
            .content(comment.getContent())
            .depth(comment.getDepth())
            .createdDate(comment.getCreatedDate())
            .build();
    }
}
