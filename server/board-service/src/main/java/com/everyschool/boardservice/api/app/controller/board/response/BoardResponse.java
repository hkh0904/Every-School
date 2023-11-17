package com.everyschool.boardservice.api.app.controller.board.response;

import com.everyschool.boardservice.domain.board.Board;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardResponse {

    private Long boardId;
    private String title;
    private String content;
    private int commentCount;
    private int scrapCount;
    private Boolean inMyScrap;
    private LocalDateTime createdDate;
    private Boolean isTapped;

    @Builder
    public BoardResponse(Long boardId, String title, String content, int commentCount, int scrapCount, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.scrapCount = scrapCount;
        this.inMyScrap = false;
        this.createdDate = createdDate;
        this.isTapped = false;
    }

    public static BoardResponse of(Board board) {
        return BoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .commentCount(board.getCommentCount())
                .scrapCount(board.getScrapCount())
                .createdDate(board.getCreatedDate())
                .build();
    }
}
