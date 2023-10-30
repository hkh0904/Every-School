package com.everyschool.consultservice.api.service.consult;

import com.everyschool.consultservice.IntegrationTestSupport;
import com.everyschool.consultservice.api.client.SchoolServiceClient;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.SchoolClassInfo;
import com.everyschool.consultservice.api.client.response.TeacherInfo;
import com.everyschool.consultservice.api.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.repository.ConsultRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.anyList;
import static org.mockito.BDDMockito.given;

class ConsultQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private ConsultQueryService consultQueryService;

    @Autowired
    private ConsultRepository consultRepository;

    @MockBean
    private UserServiceClient userServiceClient;

    @MockBean
    private SchoolServiceClient schoolServiceClient;

    @DisplayName("학부모는 등록한 상담 내역을 조회할 수 있다.")
    @Test
    void searchConsults() {
        //given
        Consult consult1 = saveConsult("우리집 고양이 이름은 리온이에요!", 3003, 2001);
        Consult consult2 = saveConsult("우리집 강아지 이름은 코코에요!", 3002, 2002);
        Consult consult3 = saveConsult("우리집 고양이 이름은 리온이에요!", 3001, 2001);

        given(userServiceClient.searchByUserKey(anyString()))
            .willReturn(10L);

        TeacherInfo teacherInfo = TeacherInfo.builder()
            .userId(100L)
            .name("이예리")
            .schoolClassId(1000L)
            .userKey(UUID.randomUUID().toString())
            .build();

        given(userServiceClient.searchTeacherByIdIn(anyList()))
            .willReturn(List.of(teacherInfo));

        SchoolClassInfo schoolClassInfo = SchoolClassInfo.builder()
            .teacherId(100L)
            .grade(1)
            .classNum(3)
            .build();

        given(schoolServiceClient.searchSchoolClassByTeacherIdIn(anyList()))
            .willReturn(List.of(schoolClassInfo));

        //when
        List<ConsultResponse> responses = consultQueryService.searchConsults(UUID.randomUUID().toString());

        //then
        assertThat(responses).hasSize(3);
    }

    @DisplayName("상담 PK로 상담 상세 내역을 조회할 수 있다.")
    @Test
    void searchConsult() {
        //given
        Consult consult = saveConsult("우리집 고양이 이름은 리온이에요!", 3003, 2001);

        TeacherInfo teacherInfo = TeacherInfo.builder()
            .userId(100L)
            .name("이예리")
            .schoolClassId(1000L)
            .userKey(UUID.randomUUID().toString())
            .build();

        given(userServiceClient.searchTeacherById(anyLong()))
            .willReturn(teacherInfo);

        SchoolClassInfo schoolClassInfo = SchoolClassInfo.builder()
            .teacherId(100L)
            .grade(1)
            .classNum(3)
            .build();

        given(schoolServiceClient.searchSchoolClassByTeacherId(anyLong()))
            .willReturn(schoolClassInfo);

        //when
        ConsultDetailResponse response = consultQueryService.searchConsult(consult.getId());

        //then
        assertThat(response).isNotNull();
    }

    public Consult saveConsult(String message, int progressStatusId, int typeId) {
        Consult consult = Consult.builder()
            .consultDateTime(LocalDateTime.now())
            .message(message)
            .schoolYear(2023)
            .progressStatusId(progressStatusId)
            .typeId(typeId)
            .schoolId(10000L)
            .parentId(10L)
            .studentId(1L)
            .teacherId(100L)
            .build();
        return consultRepository.save(consult);
    }
}