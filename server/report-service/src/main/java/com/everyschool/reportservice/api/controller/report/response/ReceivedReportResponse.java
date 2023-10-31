package com.everyschool.reportservice.api.controller.report.response;

import com.everyschool.reportservice.api.controller.report.response.vo.ReceivedReportVo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ReceivedReportResponse {

    private int count;
    private List<ReceivedReportVo> reports;

    @Builder
    public ReceivedReportResponse(int count, List<ReceivedReportVo> reports) {
        this.count = count;
        this.reports = reports;
    }
}
