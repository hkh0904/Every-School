package com.everyschool.reportservice.api.app.controller.report.response;

import com.everyschool.reportservice.domain.report.Report;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateReportResponse {

    private Long reportId;
    private String title;
    private LocalDateTime createdDate;

    @Builder
    private CreateReportResponse(Long reportId, String title, LocalDateTime createdDate) {
        this.reportId = reportId;
        this.title = title;
        this.createdDate = createdDate;
    }

    public static CreateReportResponse of(Report report) {
        return CreateReportResponse.builder()
            .reportId(report.getId())
            .title(report.getTitle())
            .createdDate(report.getCreatedDate())
            .build();
    }
}
