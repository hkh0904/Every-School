package com.everyschool.boardservice.api.controller.board.request;

import com.everyschool.boardservice.api.service.board.dto.CreateBoardDto;
import com.everyschool.boardservice.domain.board.UploadFile;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateBoardRequest {

    private String title;
    private String content;
    private Boolean isUsedComment;
    private List<MultipartFile> files = new ArrayList<>();

    @Builder
    private CreateBoardRequest(String title, String content, Boolean isUsedComment, List<MultipartFile> files) {
        this.title = title;
        this.content = content;
        this.isUsedComment = isUsedComment;
        this.files = files;
    }

    public CreateBoardDto toDto(List<UploadFile> uploadFiles) {
        return CreateBoardDto.builder()
            .title(this.title)
            .content(this.content)
            .isUsedComment(this.isUsedComment)
            .uploadFiles(uploadFiles)
            .build();
    }
}
