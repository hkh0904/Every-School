package com.everyschool.boardservice.api.controller.board.response;

import com.everyschool.boardservice.domain.board.Board;
import com.everyschool.boardservice.domain.board.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateBoardResponse {

    private Long boardId;
    private String category;
    private String title;
    private Boolean isUsedComment;
    private int fileCount;
    private LocalDateTime createdDate;

    @Builder
    private CreateBoardResponse(Long boardId, String category, String title, Boolean isUsedComment, int fileCount, LocalDateTime createdDate) {
        this.boardId = boardId;
        this.category = category;
        this.title = title;
        this.isUsedComment = isUsedComment;
        this.fileCount = fileCount;
        this.createdDate = createdDate;
    }

    public static CreateBoardResponse of(Board board) {
        return CreateBoardResponse.builder()
            .boardId(board.getId())
            .category(Category.getText(board.getCategoryId()))
            .title(board.getTitle())
            .isUsedComment(board.getIsUsedComment())
            .fileCount(board.getFiles().size())
            .createdDate(board.getCreatedDate())
            .build();
    }
}
