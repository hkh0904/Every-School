package com.everyschool.reportservice.api.web.controller.report.response;

import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ReportDetailResponse {

    private Long reportId;
    private Integer schoolYear;
    private String type;
    private String status;
    private String witness;
    private String who;
    private String when;
    private String where;
    private String what;
    private String how;
    private String why;
    private String description;
    private List<String> files;

    @Builder
    public ReportDetailResponse(Long reportId, Integer schoolYear, int typeId, int statusId, String witness, String who, String when, String where, String what, String how, String why, String description, List<String> files) {
        this.reportId = reportId;
        this.schoolYear = schoolYear;
        this.type = ReportType.getText(typeId);
        this.witness = witness;
        this.status = ProgressStatus.getText(statusId);
        this.who = who;
        this.when = when;
        this.where = where;
        this.what = what;
        this.how = how;
        this.why = why;
        this.description = description;
        this.files = files;
    }

    public static ReportDetailResponse of(Report report, List<String> files) {
        return ReportDetailResponse.builder()
            .reportId(report.getId())
            .schoolYear(report.getSchoolYear())
            .typeId(report.getTypeId())
            .statusId(report.getProgressStatusId())
            .witness(report.getTitle())
            .who(report.getContent().getReportWho())
            .when(report.getContent().getReportWhen())
            .where(report.getContent().getReportWhere())
            .what(report.getContent().getReportWhat())
            .how(report.getContent().getReportHow())
            .why(report.getContent().getReportWhy())
            .description(report.getDescription())
            .files(files)
            .build();
    }
}
