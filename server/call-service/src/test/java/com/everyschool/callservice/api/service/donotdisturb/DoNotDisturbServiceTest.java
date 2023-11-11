package com.everyschool.callservice.api.service.donotdisturb;

import com.everyschool.callservice.IntegrationTestSupport;
import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.donotdisturb.response.DoNotDisturbResponse;
import com.everyschool.callservice.api.service.donotdisturb.dto.DoNotDisturbDto;
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

public class DoNotDisturbServiceTest extends IntegrationTestSupport {

    @MockBean
    private UserServiceClient userServiceClient;
    @Autowired
    private DoNotDisturbService doNotDisturbService;
    @Autowired
    private DoNotDisturbRepository doNotDisturbRepository;

    @DisplayName("방해 금지 모드 생성")
    @Test
    void updateIsActivate() {
        // given
        UserInfo user = UserInfo.builder()
                .userId(1L)
                .userType('T')
                .userName("신성주")
                .schoolClassId(1L)
                .build();

        given(userServiceClient.searchUserInfo(anyString()))
                .willReturn(user);

        DoNotDisturbDto dto = DoNotDisturbDto.builder()
                .startTime(LocalDateTime.now().minusHours(2))
                .endTime(LocalDateTime.now().minusHours(1))
                .isActivate(false)
                .build();
        dto.setTeacherId(user.getUserId());

        // when
        DoNotDisturbResponse response = doNotDisturbService.createDoNotDisturb(dto, anyString());

        // then
        assertThat(response.isActivate()).isFalse();
    }

    @DisplayName("방해 금지 모드 끄기/켜기")
    @Test
    void createDoNotDisturb() {
        // given
        DoNotDisturb doNotDisturb = saveDoNot(1L, LocalDateTime.now(), LocalDateTime.now(), false);

        // when
        DoNotDisturbResponse response = doNotDisturbService.updateIsActivate(doNotDisturb.getId());

        // then
        assertThat(response.isActivate()).isTrue();
    }

    private DoNotDisturb saveDoNot(Long teacherId, LocalDateTime startTime, LocalDateTime endTime, Boolean isActivate) {
        DoNotDisturb doNotDisturb = DoNotDisturb.builder()
                .teacherId(teacherId)
                .startTime(startTime)
                .endTime(endTime)
                .isActivate(isActivate)
                .build();
        return doNotDisturbRepository.save(doNotDisturb);
    }
}
