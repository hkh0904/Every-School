package com.everyschool.boardservice.api.controller.freeboard.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class EditFreeBoardRequest {

    private String title;
    private String content;
    private List<MultipartFile> uploadFiles;

    @Builder
    private EditFreeBoardRequest(String title, String content, List<MultipartFile> uploadFiles) {
        this.title = title;
        this.content = content;
        this.uploadFiles = uploadFiles;
    }
}
