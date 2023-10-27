package com.everyschool.callservice.api.service.call;

import com.everyschool.callservice.IntegrationTestSupport;
import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.client.response.UserInfo;
import com.everyschool.callservice.api.controller.call.response.CallResponse;
import com.everyschool.callservice.api.service.call.dto.CreateCallDto;
import com.everyschool.callservice.domain.call.Call;
import com.everyschool.callservice.domain.call.repository.CallRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CallServiceTest extends IntegrationTestSupport {

    @Autowired
    private CallService callService;

    @Autowired
    private CallRepository callRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @DisplayName("통화 종료후 DB에 저장")
    @Test
    void createCallInfo() {

        // given
        UserInfo teacher = UserInfo.builder()
                .userId(1L)
                .userType('T')
                .userName("신성주")
                .schoolClassId(1L)
                .build();

        UserInfo parent = UserInfo.builder()
                .userId(2L)
                .userType('M')
                .userName("홍경환")
                .schoolClassId(1L)
                .build();

//        given(userServiceClient.searchUserInfo("jwt"))
//                .willReturn(teacher);
//        given(userServiceClient.searchUserInfoByUserKey("otheruserkey"))
//                .willReturn(parent);

        CreateCallDto dto = CreateCallDto.builder()
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(2))
                .endDateTime(LocalDateTime.now().minusHours(1))
                .uploadFileName("이예리 폭언 녹음본")
                .storeFileName("이예리 폭언 녹음본")
                .isBad(true)
                .build();

        // when
        CallResponse response = callService.createCallInfo(dto, "otheruserkey", "jwt");

        // then
        assertThat(response.getOtherUserName()).isEqualTo("홍경환");
        assertThat(response.getTeacherName()).isEqualTo("신성주");
        assertThat(response.getUploadFileName()).isEqualTo("이예리 폭언 녹음본");
    }

    private Call saveCall() {
        Call call = Call.builder()
                .teacherId(1L)
                .otherUserId(2L)
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(2))
                .endDateTime(LocalDateTime.now().minusHours(1))
                .uploadFileName("이예리 폭언 녹음본")
                .storeFileName("이예리 폭언 녹음본")
                .isBad(true)
                .build();
        return callRepository.save(call);
    }
}