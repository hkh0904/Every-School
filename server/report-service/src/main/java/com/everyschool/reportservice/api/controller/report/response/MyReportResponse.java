package com.everyschool.reportservice.api.controller.report.response;

import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.ReportType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyReportResponse {

    private Long reportId;
    private String type;
    private String progressStatus;
    private LocalDateTime reportDate;

    @Builder
    public MyReportResponse(Long reportId, int typeId, int progressStatusId, LocalDateTime reportDate) {
        this.reportId = reportId;
        this.type = ReportType.getText(typeId);
        this.progressStatus = ProgressStatus.getText(progressStatusId);
        this.reportDate = reportDate;
    }
}
