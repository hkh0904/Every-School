package com.everyschool.reportservice.api.app.controller.report.response;

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
    private String result;
    private List<String> files;

    @Builder
    public ReportDetailResponse(Long reportId, Integer schoolYear, int type, int status, String witness, String who, String when, String where, String what, String how, String why, String description, String result, List<String> files) {
        this.reportId = reportId;
        this.schoolYear = schoolYear;
        this.type = ReportType.getText(type);
        this.status = ProgressStatus.getText(status);
        this.witness = witness;
        this.who = who;
        this.when = when;
        this.where = where;
        this.what = what;
        this.how = how;
        this.why = why;
        this.description = description;
        this.result = result;
        this.files = files;
    }

    public static ReportDetailResponse of(Report report, List<String> files) {
        return ReportDetailResponse.builder()
            .reportId(report.getId())
            .schoolYear(report.getSchoolYear())
            .type(report.getTypeId())
            .status(report.getProgressStatusId())
            .witness(report.getWitness())
            .who(report.getContent().getReportWho())
            .when(report.getContent().getReportWhen())
            .where(report.getContent().getReportWhere())
            .what(report.getContent().getReportWhat())
            .how(report.getContent().getReportHow())
            .why(report.getContent().getReportWhy())
            .description(report.getDescription())
            .result(report.getResult())
            .files(files)
            .build();
    }

}
