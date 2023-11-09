package com.everyschool.reportservice.api.web.service.report;

import com.everyschool.reportservice.IntegrationTestSupport;
import com.everyschool.reportservice.api.web.controller.report.response.ReportDetailResponse;
import com.everyschool.reportservice.api.web.controller.report.response.ReportResponse;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportContent;
import com.everyschool.reportservice.domain.report.ReportType;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.everyschool.reportservice.domain.report.ProgressStatus.*;
import static org.assertj.core.api.Assertions.*;

class ReportWebQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private ReportWebQueryService reportWebQueryService;

    @Autowired
    private ReportRepository reportRepository;

    @DisplayName("학교 아이디, 학년도, 처리 상태를 입력 받아 신고 목록 조회를 할 수 있다.")
    @Test
    void searchReports() {
        //given
        Report report1 = saveReport(100000L, 2023);

        Report report2 = saveReport(1L, 2023);

        Report report3 = saveReport(100000L, 2022);

        Report report4 = saveReport(100000L, 2023);
        report4.remove();

        Report report5 = saveReport(100000L, 2023);
        report5.editStatus(FINISH.getCode());

        //when
        List<ReportResponse> responses = reportWebQueryService.searchReports(100000L, 2023, REGISTER.getCode());

        //then
        assertThat(responses).hasSize(1)
            .extracting("status")
            .containsExactlyInAnyOrder(REGISTER.getText());
    }

    @DisplayName("신고 아이디를 입력 받아 신고 내역을 상세 조회를 할 수 있다.")
    @Test
    void searchReport() {
        //given
        Report report = saveReport(100000L, 2023);

        //when
        ReportDetailResponse response = reportWebQueryService.searchReport(report.getId());

        //then
        assertThat(response.getFiles()).hasSize(0);
    }

    private Report saveReport(long schoolId, int schoolYear) {
        ReportContent content = ReportContent.builder()
            .reportWho("who")
            .reportWhen("when")
            .reportWhere("where")
            .reportWhat("what")
            .reportHow("how")
            .reportWhy("why")
            .build();

        Report report = Report.builder()
            .title("title")
            .description("description")
            .content(content)
            .schoolYear(schoolYear)
            .typeId(ReportType.VIOLENCE.getCode())
            .schoolId(schoolId)
            .userId(1L)
            .build();

        return reportRepository.save(report);
    }
}