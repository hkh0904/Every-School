package com.everyschool.boardservice.api.controller.board.response;

import com.everyschool.boardservice.domain.board.Board;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FreeBoardResponse {

    private Long boardId;
    private String title;
    private String content;
    private int commentCount;
    private LocalDateTime createdDate;
    private String imageUrl;

    @Builder
    private FreeBoardResponse(Long boardId, String title, String content, int commentCount, LocalDateTime createdDate, String imageUrl) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
        this.imageUrl = imageUrl;
    }

    public static FreeBoardResponse of(Board board, String imageUrl) {
        return FreeBoardResponse.builder()
            .boardId(board.getId())
            .title(board.getTitle())
            .content(board.getContent())
            .commentCount(board.getCommentCount())
            .createdDate(board.getCreatedDate())
            .imageUrl(imageUrl)
            .build();
    }
}
