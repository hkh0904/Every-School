package com.everyschool.reportservice.api.service.report;

import com.everyschool.reportservice.IntegrationTestSupport;
import com.everyschool.reportservice.api.client.UserServiceClient;
import com.everyschool.reportservice.api.client.response.UserInfo;
import com.everyschool.reportservice.api.controller.report.response.CreateReportResponse;
import com.everyschool.reportservice.api.service.report.dto.CreateReportDto;
import com.everyschool.reportservice.domain.report.ReportType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

class ReportServiceTest extends IntegrationTestSupport {

    @Autowired
    private ReportService reportService;

    @MockBean
    private UserServiceClient userServiceClient;


    @DisplayName("신고 정보를 입력 받아 신고를 등록할 수 있다.")
    @Test
    void createReport() {
        //given
        UserInfo userInfo = UserInfo.builder()
            .userId(1L)
            .userType('S')
            .userName("이예리")
            .schoolClassId(100L)
            .build();

        given(userServiceClient.searchByUserKey(anyString()))
            .willReturn(userInfo);

        CreateReportDto dto = CreateReportDto.builder()
            .typeId(ReportType.ETC.getCode())
            .title("리온이가 너무 귀여워요!")
            .who("이리온")
            .when("2023-10-31 10:30")
            .where("우리집")
            .what("애교쟁이에요.")
            .how(null)
            .why(null)
            .build();

        //when
        CreateReportResponse response = reportService.createReport(getUUID(), 10000L, dto, new ArrayList<>());

        //then
        Assertions.assertThat(response.getTitle()).isEqualTo("리온이가 너무 귀여워요!");
    }

    private String getUUID() {
        return UUID.randomUUID().toString();
    }

}