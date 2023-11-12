package com.everyschool.reportservice.api.app.service.report;

import com.everyschool.reportservice.IntegrationTestSupport;
import com.everyschool.reportservice.api.app.controller.report.response.CreateReportResponse;
import com.everyschool.reportservice.api.app.controller.report.response.RemoveReportResponse;
import com.everyschool.reportservice.api.app.service.report.dto.CreateReportDto;
import com.everyschool.reportservice.api.client.SchoolServiceClient;
import com.everyschool.reportservice.api.client.UserServiceClient;
import com.everyschool.reportservice.api.client.response.SchoolUserInfo;
import com.everyschool.reportservice.api.client.response.UserInfo;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportContent;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.everyschool.reportservice.domain.report.ReportType.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

class ReportAppServiceTest extends IntegrationTestSupport {

    @Autowired
    private ReportAppService reportAppService;

    @Autowired
    private ReportRepository reportRepository;

    @MockBean
    private UserServiceClient userServiceClient;

    @MockBean
    private SchoolServiceClient schoolServiceClient;

    @DisplayName("회원 고유키, 학년도, 학교 아이디, 신고 정보, 첨부 파일을 입력받아 신고를 등록할 수 있다.")
    @Test
    void createReport() {
        //given
        givenUserInfo();
        givenSchoolUserInfo();
        CreateReportDto dto = createReportDto();

        //when
        CreateReportResponse response = reportAppService.createReport(UUID.randomUUID().toString(), 2023, 100000L, dto, new ArrayList<>());

        //then
        Optional<Report> findReport = reportRepository.findById(response.getReportId());
        assertThat(findReport).isPresent();
    }

    @DisplayName("신고 아이디를 입력받아 신고 내역을 삭제할 수 있다.")
    @Test
    void removeReport() {
        //given
        Report report = saveReport();

        //when
        RemoveReportResponse removeReportResponse = reportAppService.removeReport(report.getId());

        //then
        Optional<Report> findReport = reportRepository.findById(report.getId());
        assertThat(findReport).isPresent();
        assertThat(findReport.get().isDeleted()).isTrue();
    }

    private void givenUserInfo() {
        UserInfo userInfo = UserInfo.builder()
            .userId(1L)
            .userType('S')
            .userName("이예리")
            .schoolClassId(2L)
            .build();

        given(userServiceClient.searchByUserKey(anyString()))
            .willReturn(userInfo);
    }

    private void givenSchoolUserInfo() {
        SchoolUserInfo schoolUserInfo = SchoolUserInfo.builder()
            .grade(1)
            .classNum(3)
            .studentNum(10313)
            .build();

        given(schoolServiceClient.searchByUserId(anyLong()))
            .willReturn(schoolUserInfo);
    }

    private CreateReportDto createReportDto() {
        return CreateReportDto.builder()
            .typeId(VIOLENCE.getCode())
            .description("신성주가 자꾸 괴롭혀요.")
            .who("신성주")
            .when("2023-11-13 10시경")
            .where("학교 급식실")
            .what("제 휘낭시에를 뺏어갔어요.")
            .how(null)
            .why(null)
            .build();
    }

    private Report saveReport() {
        ReportContent content = ReportContent.builder()
            .reportWho("신성주")
            .reportWhen("2023-11-13 10시경")
            .reportWhere("학교 급식실")
            .reportWhat("제 휘낭시에를 뺏어갔어요.")
            .reportHow(null)
            .reportWhy(null)
            .build();

        Report report = Report.builder()
            .witness("1학년 3번 13번 이예리 학생")
            .description("신성주가 자꾸 괴롭혀요.")
            .content(content)
            .schoolYear(2023)
            .typeId(VIOLENCE.getCode())
            .schoolId(100000L)
            .userId(1L)
            .build();

        return reportRepository.save(report);
    }
}