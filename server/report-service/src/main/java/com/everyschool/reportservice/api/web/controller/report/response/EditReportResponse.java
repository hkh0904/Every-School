package com.everyschool.reportservice.api.web.controller.report.response;

import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.Report;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EditReportResponse {

    private Long reportId;
    private String status;
    private LocalDateTime lastModifiedDate;

    @Builder
    private EditReportResponse(Long reportId, int statusId, LocalDateTime lastModifiedDate) {
        this.reportId = reportId;
        this.status = ProgressStatus.getText(statusId);
        this.lastModifiedDate = lastModifiedDate;
    }

    public static EditReportResponse of(Report report) {
        return EditReportResponse.builder()
            .reportId(report.getId())
            .statusId(report.getProgressStatusId())
            .lastModifiedDate(report.getLastModifiedDate())
            .build();
    }
}
