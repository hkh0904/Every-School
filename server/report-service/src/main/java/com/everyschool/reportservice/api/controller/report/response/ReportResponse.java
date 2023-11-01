package com.everyschool.reportservice.api.controller.report.response;

import com.everyschool.reportservice.domain.report.ReportType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReportResponse {

    private int count;
    private List<ReportVo> reports;

    @Builder
    public ReportResponse(int count, List<ReportVo> reports) {
        this.count = count;
        this.reports = reports;
    }

    @Data
    public static class ReportVo {

        private int no;
        private Long reportId;
        private String type;
        private LocalDateTime date;

        @Builder
        public ReportVo(Long reportId, int typeId, LocalDateTime date) {
            this.reportId = reportId;
            this.type = ReportType.getText(typeId);
            this.date = date;
        }
    }
}
