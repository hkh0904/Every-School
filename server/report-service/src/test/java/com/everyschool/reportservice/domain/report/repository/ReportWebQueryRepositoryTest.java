package com.everyschool.reportservice.domain.report.repository;

import com.everyschool.reportservice.IntegrationTestSupport;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportContent;
import com.everyschool.reportservice.domain.report.ReportType;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.*;

class ReportWebQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ReportWebQueryRepository reportWebQueryRepository;

    @Autowired
    private ReportRepository reportRepository;

    @DisplayName("학교 아이디, 학년도, 처리 상태가 일치하는 신고 내역을 조회한다.")
    @Test
    void findByCond() {
        //given
        Report report1 = saveReport(100000L, 2023);

        Report report2 = saveReport(1L, 2023);

        Report report3 = saveReport(100000L, 2022);

        Report report4 = saveReport(100000L, 2023);
        report4.remove();

        Report report5 = saveReport(100000L, 2023);
        report5.editStatus(ProgressStatus.FINISH.getCode());

        //when
        List<Report> reports = reportWebQueryRepository.findByCond(100000L, 2023, ProgressStatus.REGISTER.getCode());

        //then
        assertThat(reports).hasSize(1)
            .extracting("schoolId", "schoolYear", "progressStatusId")
            .containsExactlyInAnyOrder(
                tuple(100000L, 2023, ProgressStatus.REGISTER.getCode())
            );
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