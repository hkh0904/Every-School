package com.everyschool.reportservice.api.app.controller.report.response;

import com.everyschool.reportservice.domain.report.Report;
import lombok.Data;

@Data
public class ReportDetailResponse {

    private Long reportId;
    private String type;
    private String status;

    public static ReportDetailResponse of(Report report) {
        return null;
    }

}
