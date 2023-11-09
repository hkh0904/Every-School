package com.everyschool.reportservice.api.web.service.report;

import com.everyschool.reportservice.IntegrationTestSupport;
import com.everyschool.reportservice.api.web.controller.report.response.EditReportResponse;
import com.everyschool.reportservice.domain.report.ProgressStatus;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportContent;
import com.everyschool.reportservice.domain.report.ReportType;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.everyschool.reportservice.domain.report.ProgressStatus.*;
import static com.everyschool.reportservice.error.ErrorMessage.UNREGISTERED_REPORT;
import static org.assertj.core.api.Assertions.*;

class ReportWebServiceTest extends IntegrationTestSupport {

    @Autowired
    private ReportWebService reportWebService;

    @Autowired
    private ReportRepository reportRepository;

    @DisplayName("등록되지 않은 신고의 진행 상태를 수정하는 경우 예외가 발생한다.")
    @Test
    void editStatusUnregisteredReport() {
        //given

        //when //then
        assertThatThrownBy(() -> reportWebService.editStatus(1L, PROCESS.getCode()))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage(UNREGISTERED_REPORT.getMessage());
    }

    @DisplayName("신고 아이디와 상태 코드를 입력 받아 진행 상태를 수정할 수 있다.")
    @Test
    void editStatus() {
        //given
        Report report = saveReport();

        //when
        EditReportResponse response = reportWebService.editStatus(report.getId(), PROCESS.getCode());

        //then
        assertThat(response.getStatus()).isEqualTo(PROCESS.getText());
    }



    private Report saveReport() {
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
            .schoolYear(2023)
            .typeId(ReportType.VIOLENCE.getCode())
            .schoolId(100000L)
            .userId(1L)
            .build();

        return reportRepository.save(report);
    }
}