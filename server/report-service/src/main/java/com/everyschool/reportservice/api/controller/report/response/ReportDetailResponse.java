package com.everyschool.reportservice.api.controller.report.response;

import com.everyschool.reportservice.api.client.response.StudentInfo;
import com.everyschool.reportservice.api.client.response.UserInfo;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDetailResponse {

    private Long reportId;
    private String reportUser;
    private String type;
    private String progressStatus;
    private String title;
    private String description;
    private String who;
    private String when;
    private String where;
    private String what;
    private String how;
    private String why;
    private String result;
    private LocalDateTime reportDate;

    @Builder
    private ReportDetailResponse(Long reportId, String reportUser, Integer typeId, Integer progressStatusId, String title, String description, String who, String when, String where, String what, String how, String why, String result, LocalDateTime reportDate) {
        this.reportId = reportId;
        this.reportUser = reportUser;
        this.type = ReportType.getText(typeId);
        this.progressStatus = ProgressStatus.getText(progressStatusId);
        this.title = title;
        this.description = description;
        this.who = who;
        this.when = when;
        this.where = where;
        this.what = what;
        this.how = how;
        this.why = why;
        this.result = result;
        this.reportDate = reportDate;
    }

    public static ReportDetailResponse of(Report report, UserInfo userInfo, StudentInfo studentInfo) {
        return ReportDetailResponse.builder()
            .reportId(report.getId())
            .reportUser(String.format("%d학년 %d반 %d번 %s", studentInfo.getGrade(), studentInfo.getClassNum(), studentInfo.getStudentNum(), userInfo.getUserName()))
            .typeId(report.getTypeId())
            .progressStatusId(report.getProgressStatusId())
            .title(report.getTitle())
            .description(report.getDescription())
            .who(report.getContent().getReportWho())
            .when(report.getContent().getReportWhen())
            .where(report.getContent().getReportWhere())
            .what(report.getContent().getReportWhat())
            .how(report.getContent().getReportHow())
            .why(report.getContent().getReportWhy())
            .result(report.getResult())
            .reportDate(report.getCreatedDate())
            .build();
    }
}
