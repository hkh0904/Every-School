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
    private String result;
    private LocalDateTime lastModifiedDate;

    @Builder
    private EditReportResponse(Long reportId, int statusId, String result, LocalDateTime lastModifiedDate) {
        this.reportId = reportId;
        this.status = ProgressStatus.getText(statusId);
        this.result = result;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static EditReportResponse of(Report report) {
        return EditReportResponse.builder()
            .reportId(report.getId())
            .statusId(report.getProgressStatusId())
            .result(report.getResult())
            .lastModifiedDate(report.getLastModifiedDate())
            .build();
    }
}
