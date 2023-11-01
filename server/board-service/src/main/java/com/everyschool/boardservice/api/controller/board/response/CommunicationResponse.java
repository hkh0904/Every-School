package com.everyschool.boardservice.api.controller.board.response;

import com.everyschool.boardservice.domain.board.Board;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommunicationResponse {

    private Long boardId;
    private String title;
    private String content;
    private int commentCount;
    private LocalDateTime createdDate;

    @Builder
    private CommunicationResponse(Long boardId, String title, String content, int commentCount, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
    }

    public static CommunicationResponse of(Board board) {
        return CommunicationResponse.builder()
            .boardId(board.getId())
            .title(board.getTitle())
            .content(board.getContent())
            .commentCount(board.getCommentCount())
            .createdDate(board.getCreatedDate())
            .build();

    }
}
