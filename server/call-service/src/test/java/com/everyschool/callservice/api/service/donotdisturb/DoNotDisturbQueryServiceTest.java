package com.everyschool.callservice.api.service.donotdisturb;

import com.everyschool.callservice.IntegrationTestSupport;
import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.donotdisturb.response.DoNotDisturbResponse;
import com.everyschool.callservice.domain.donotdisturb.DoNotDisturb;
import com.everyschool.callservice.domain.donotdisturb.repository.DoNotDisturbRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class DoNotDisturbQueryServiceTest extends IntegrationTestSupport {

    @MockBean
    private UserServiceClient userServiceClient;
    @Autowired
    private DoNotDisturbRepository doNotDisturbRepository;
    @Autowired
    private DoNotDisturbQueryService doNotDisturbQueryService;

    @DisplayName("최근 등록한 방해 금지 목록 불러오기")
    @Test
    void searchMyDoNotDisturb() {

        // given
        UserInfo teacher = UserInfo.builder()
                .userId(1L)
                .userType('T')
                .userName("오연주선생님")
                .schoolClassId(1L)
                .build();

        String token = anyString();

        given(userServiceClient.searchUserInfo(token))
                .willReturn(teacher);

        DoNotDisturb d1 = saveD(1L, LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4), false);
        DoNotDisturb d2 = saveD(1L, LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2), false);
        DoNotDisturb d3 = saveD(2L, LocalDateTime.now().minusHours(1), LocalDateTime.now().minusMinutes(30), true);
        DoNotDisturb d4 = saveD(3L, LocalDateTime.now().minusMinutes(20), LocalDateTime.now().minusMinutes(10), false);

        // when
        DoNotDisturbResponse response = doNotDisturbQueryService.searchMyDoNotDisturb(token);

        // then
        assertThat(response.getDoNotDisturbId()).isEqualTo(d2.getId());
        assertThat(response.isActivate()).isFalse();

    }

    private DoNotDisturb saveD(Long teacherId, LocalDateTime startTime, LocalDateTime endTime, Boolean isActivate) {
        DoNotDisturb userD = DoNotDisturb.builder()
                .teacherId(teacherId)
                .startTime(startTime)
                .endTime(endTime)
                .isActivate(isActivate)
                .build();
        return doNotDisturbRepository.save(userD);
    }
}