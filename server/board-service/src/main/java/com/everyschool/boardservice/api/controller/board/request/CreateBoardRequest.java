package com.everyschool.boardservice.api.controller.board.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBoardRequest {

    private String title;
    private String content;
    private Long categoryId;
    private Long schoolId;
    private Long classId;
    private Boolean useComment;

    @Builder
    private CreateBoardRequest(String title, String content, Long categoryId, Long schoolId, Long classId, Boolean useComment) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.schoolId = schoolId;
        this.classId = classId;
        this.useComment = useComment;
    }
}
