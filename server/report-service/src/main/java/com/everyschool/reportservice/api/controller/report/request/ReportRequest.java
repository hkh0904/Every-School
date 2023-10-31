package com.everyschool.reportservice.api.controller.report.request;

import com.everyschool.reportservice.api.service.report.dto.CreateReportDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReportRequest {

    private Integer typeId;
    private String title;
    private String description;
    private String who;
    private String when;
    private String where;
    private String what;
    private String how;
    private String why;
    private List<MultipartFile> files = new ArrayList<>();

    @Builder
    private ReportRequest(Integer typeId, String title, String description, String who, String when, String where, String what, String how, String why, List<MultipartFile> files) {
        this.typeId = typeId;
        this.title = title;
        this.description = description;
        this.who = who;
        this.when = when;
        this.where = where;
        this.what = what;
        this.how = how;
        this.why = why;
        this.files = files;
    }

    public CreateReportDto toDto() {
        return CreateReportDto.builder()
            .typeId(this.typeId)
            .title(this.title)
            .description(this.description)
            .who(this.who)
            .when(this.when)
            .where(this.where)
            .what(this.what)
            .how(this.how)
            .why(this.why)
            .build();
    }
}