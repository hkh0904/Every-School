package com.everyschool.reportservice.api.service.report.dto;

import com.everyschool.reportservice.domain.report.ReportContent;
import lombok.Builder;
import lombok.Data;

@Data
public class CreateReportDto {

    private Integer typeId;
    private String title;
    private String description;
    private String who;
    private String when;
    private String where;
    private String what;
    private String how;
    private String why;

    @Builder
    private CreateReportDto(Integer typeId, String title, String description, String who, String when, String where, String what, String how, String why) {
        this.typeId = typeId;
        this.title = title;
        this.description = description;
        this.who = who;
        this.when = when;
        this.where = where;
        this.what = what;
        this.how = how;
        this.why = why;
    }

    public ReportContent toContent() {
        return ReportContent.builder()
            .reportWho(this.who)
            .reportWhen(this.when)
            .reportWhere(this.where)
            .reportWhat(this.what)
            .reportHow(this.how)
            .reportWhy(this.why)
            .build();
    }
}
