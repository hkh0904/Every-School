package com.everyschool.reportservice.api.controller.school.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileResponse {
    private String uploadFileName;

    @Builder
    public FileResponse(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }
}
