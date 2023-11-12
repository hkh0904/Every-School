package com.everyschool.reportservice.api.app.controller.report.response;

import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RemoveReportResponse {

    private Long reportId;
    private String type;
    private Integer schoolYear;
    private String witness;
    private LocalDateTime removedDate;

    @Builder
    private RemoveReportResponse(Long reportId, String type, Integer schoolYear, String witness, LocalDateTime removedDate) {
        this.reportId = reportId;
        this.type = type;
        this.schoolYear = schoolYear;
        this.witness = witness;
        this.removedDate = removedDate;
    }

    public static RemoveReportResponse of(Report report) {
        return RemoveReportResponse.builder()
            .reportId(report.getId())
            .type(ReportType.getText(report.getTypeId()))
            .schoolYear(report.getSchoolYear())
            .witness(report.getWitness())
            .removedDate(report.getLastModifiedDate())
            .build();
    }
}
