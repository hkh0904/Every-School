package com.everyschool.reportservice.api.web.controller.report.response;

import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportResponse {

    private Long reportId;
    private String type;
    private String status;
    private LocalDateTime date;

    @Builder
    public ReportResponse(Long reportId, int typeId, int statusId, LocalDateTime date) {
        this.reportId = reportId;
        this.type = ReportType.getText(typeId);
        this.status = ProgressStatus.getText(statusId);
        this.date = date;
    }

    public static ReportResponse of(Report report) {
        return ReportResponse.builder()
            .reportId(report.getId())
            .typeId(report.getTypeId())
            .statusId(report.getProgressStatusId())
            .date(report.getLastModifiedDate())
            .build();
    }
}
