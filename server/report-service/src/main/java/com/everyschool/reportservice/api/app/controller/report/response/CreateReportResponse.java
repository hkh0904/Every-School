package com.everyschool.reportservice.api.app.controller.report.response;

import com.everyschool.reportservice.domain.report.Report;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateReportResponse {

    private Long reportId;
    private String witness;
    private LocalDateTime createdDate;

    @Builder
    private CreateReportResponse(Long reportId, String witness, LocalDateTime createdDate) {
        this.reportId = reportId;
        this.witness = witness;
        this.createdDate = createdDate;
    }

    public static CreateReportResponse of(Report report) {
        return CreateReportResponse.builder()
            .reportId(report.getId())
            .witness(report.getWitness())
            .createdDate(report.getCreatedDate())
            .build();
    }
}
