package com.everyschool.reportservice.api.controller.school.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReportRequest {
    private int codeId;
    private int schoolId;
//    private int userId;

    private int year;

    private String reportTitle;
    private String reportWho;
    private String reportWhen;
    private String reportWhere;
    private String reportWhat;
    private String reportHow;
    private String reportWhy;

    private List<FileRequest> uploadFiles;

    @Builder
    public ReportRequest(int codeId, int schoolId, int year, String reportTitle, String reportWho,
                         String reportWhen, String reportWhere, String reportWhat, String reportHow, String reportWhy,
                         List<FileRequest> uploadFiles) {
        this.codeId = codeId;
        this.schoolId = schoolId;
        this.year = year;
        this.reportTitle = reportTitle;
        this.reportWho = reportWho;
        this.reportWhen = reportWhen;
        this.reportWhere = reportWhere;
        this.reportWhat = reportWhat;
        this.reportHow = reportHow;
        this.reportWhy = reportWhy;
        this.uploadFiles = uploadFiles;
    }

//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
}
