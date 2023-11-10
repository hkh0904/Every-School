package com.everyschool.reportservice.domain.report.repository;

import com.everyschool.reportservice.IntegrationTestSupport;
import com.everyschool.reportservice.api.app.controller.report.response.ReportResponse;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportContent;
import com.everyschool.reportservice.domain.report.ReportType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.everyschool.reportservice.domain.report.ProgressStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

class ReportAppQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ReportAppQueryRepository reportAppQueryRepository;

    @Autowired
    private ReportRepository reportRepository;

    @DisplayName("회원 아이디와 학년도로 본인의 신고 내역을 조회한다.")
    @Test
    void findByUserId() {
        //given
        Report report1 = saveReport(2023, 1L);

        Report report2 = saveReport(2022, 1L);

        Report report3 = saveReport(2023, 1L);
        report3.remove();

        Report report4 = saveReport(2023, 1L);
        report4.editStatus(PROCESS.getCode());

        Report report5 = saveReport(2023, 1L);
        report5.editStatus(FINISH.getCode());

        Report report6 = saveReport(2023, 2L);

        //when
        List<ReportResponse> responses = reportAppQueryRepository.findByUserId(1L, 2023);

        //then
        assertThat(responses).hasSize(3)
            .extracting("status")
            .containsExactlyInAnyOrder(
                REGISTER.getText(),
                PROCESS.getText(),
                FINISH.getText()
            );
    }

    private Report saveReport(int schoolYear, long userId) {
        ReportContent content = ReportContent.builder()
            .reportWho("who")
            .reportWhen("when")
            .reportWhere("where")
            .reportWhat("what")
            .reportHow("how")
            .reportWhy("why")
            .build();

        Report report = Report.builder()
            .witness("witness")
            .description("description")
            .content(content)
            .schoolYear(schoolYear)
            .typeId(ReportType.VIOLENCE.getCode())
            .schoolId(100000L)
            .userId(userId)
            .build();

        return reportRepository.save(report);
    }
}