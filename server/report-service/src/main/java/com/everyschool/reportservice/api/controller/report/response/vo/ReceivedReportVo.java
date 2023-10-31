package com.everyschool.reportservice.api.controller.report.response.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceivedReportVo {

    private int no;
    private Long reportId;
    private String type;
    private LocalDateTime receivedDate;

    @Builder
    public ReceivedReportVo(Long reportId, String type, LocalDateTime receivedDate) {
        this.reportId = reportId;
        this.type = type;
        this.receivedDate = receivedDate;
    }
}
