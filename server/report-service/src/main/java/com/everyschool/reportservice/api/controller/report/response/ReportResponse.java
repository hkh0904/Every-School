package com.everyschool.reportservice.api.controller.report.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse {
    // 신고 유형
    private String reportType;
    // 신고자
    private UserResponse user;

    private String reportTitle;
    private String reportWho;
    private String reportWhen;
    private String reportWhere;
    private String reportWhat;
    private String reportHow;
    private String reportWhy;
    private List<FileResponse> uploadFiles;

    private String result;
    private LocalDateTime createdDate;

    @Builder
    public ReportResponse(String reportType, UserResponse user, String reportTitle, String reportWho, String reportWhen, String reportWhere,
                          String reportWhat, String reportHow, String reportWhy, List<FileResponse> uploadFiles,
                          String result, LocalDateTime createdDate) {
        this.reportType = reportType;
        this.user = user;
        this.reportTitle = reportTitle;
        this.reportWho = reportWho;
        this.reportWhen = reportWhen;
        this.reportWhere = reportWhere;
        this.reportWhat = reportWhat;
        this.reportHow = reportHow;
        this.reportWhy = reportWhy;
        this.uploadFiles = uploadFiles;
        this.result = result;
        this.createdDate = createdDate;
    }
}
