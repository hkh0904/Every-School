package com.everyschool.consultservice.api.service.consult;

import com.everyschool.consultservice.IntegrationTestSupport;
import com.everyschool.consultservice.api.client.SchoolServiceClient;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.SchoolClassInfo;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.api.controller.consult.response.CreateConsultResponse;
import com.everyschool.consultservice.api.service.consult.dto.CreateConsultDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

class ConsultServiceTest extends IntegrationTestSupport {

    @Autowired
    private ConsultService consultService;

    @MockBean
    private UserServiceClient userServiceClient;

    @MockBean
    private SchoolServiceClient schoolServiceClient;

    @DisplayName("상담 신청 정보를 입력 받아 상담을 등록한다.")
    @Test
    void createConsult() {
        //given
        UserInfo userInfo = UserInfo.builder()
            .userId(10L)
            .userType('F')
            .userName("이예리")
            .schoolClassId(null)
            .build();

        given(userServiceClient.searchByUserKey(anyString()))
            .willReturn(userInfo);

        SchoolClassInfo schoolClassInfo = SchoolClassInfo.builder()
            .teacherId(100L)
            .grade(1)
            .classNum(3)
            .build();

        given(schoolServiceClient.searchSchoolClassByTeacherId(anyLong()))
            .willReturn(schoolClassInfo);

        CreateConsultDto dto = CreateConsultDto.builder()
            .consultDateTime(LocalDateTime.now())
            .message("리온이는 너무 귀여워요!")
            .schoolYear(2023)
            .typeId(2001)
            .teacherKey(UUID.randomUUID().toString())
            .studentKey(UUID.randomUUID().toString())
            .build();

        //when
        CreateConsultResponse response = consultService.createConsult(UUID.randomUUID().toString(), 10000L, dto);

        //then
        assertThat(response).isNotNull();
    }

}