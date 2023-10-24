package com.everyschool.reportservice.api.controller.report.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileRequest {
//    private Long reportId;
    private String uploadFileName;

    @Builder
    public FileRequest(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

//    public void setReportId(Long reportId) {
//        this.reportId = reportId;
//    }
}
