package com.everyschool.reportservice.api.app.controller.report.response;

import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.ReportType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportResponse {

    private Long reportId;
    private String type;
    private String status;
    private LocalDateTime createdDate;

    @Builder
    public ReportResponse(Long reportId, int typeId, int statusId, LocalDateTime createdDate) {
        this.reportId = reportId;
        this.type = ReportType.getText(typeId);
        this.status = ProgressStatus.getText(statusId);
        this.createdDate = createdDate;
    }
}
