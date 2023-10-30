package com.everyschool.consultservice.api.service.consult;

import com.everyschool.consultservice.IntegrationTestSupport;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.api.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.Title;
import com.everyschool.consultservice.domain.consult.repository.ConsultRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class ConsultQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private ConsultQueryService consultQueryService;

    @Autowired
    private ConsultRepository consultRepository;

    @MockBean
    private UserServiceClient userServiceClient;

    @DisplayName("학부모는 등록한 상담 내역을 조회할 수 있다.")
    @Test
    void searchConsults() {
        //given
        Consult consult1 = saveConsult("우리집 고양이 이름은 리온이에요!", 3003, 2001);
        Consult consult2 = saveConsult("우리집 강아지 이름은 코코에요!", 3002, 2002);
        Consult consult3 = saveConsult("우리집 고양이 이름은 리온이에요!", 3001, 2001);

        UserInfo userInfo = UserInfo.builder()
            .userId(10L)
            .userType('F')
            .userName("이예리")
            .schoolClassId(null)
            .build();

        given(userServiceClient.searchByUserKey(anyString()))
            .willReturn(userInfo);

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

        //when
        ConsultDetailResponse response = consultQueryService.searchConsult(consult.getId());

        //then
        assertThat(response).isNotNull();
    }

    public Consult saveConsult(String message, int progressStatusId, int typeId) {
        Title title = Title.builder()
            .parentTitle("1학년 3반 강형욱 선생님")
            .teacherTitle("1학년 3반 이리온(모) 이예리")
            .build();

        Consult consult = Consult.builder()
            .consultDateTime(LocalDateTime.now())
            .message(message)
            .title(title)
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