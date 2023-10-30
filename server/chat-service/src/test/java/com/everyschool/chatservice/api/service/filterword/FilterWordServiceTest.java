package com.everyschool.chatservice.api.service.filterword;

import com.everyschool.chatservice.IntegrationTestSupport;
import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.service.filterword.dto.CreateFilterWordDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class FilterWordServiceTest extends IntegrationTestSupport {

    @Autowired
    private FilterWordService filterWordService;

    @MockBean
    private UserServiceClient userServiceClient;

    @DisplayName("필터 단어 등록은 관리자 계정이 아니면 예외가 발생한다.")
    @Test
    void createFilterWordErrorAuth() {
        //given
        UserInfo parent = UserInfo.builder()
                .userId(1L)
                .userType('M')
                .userName("신성주")
                .schoolClassId(1L)
                .build();

        given(userServiceClient.searchUserInfo(anyString()))
                .willReturn(parent);

        CreateFilterWordDto dto = CreateFilterWordDto.builder()
                .loginUserToken("jwt")
                .word("심한욕")
                .build();
        //when

        //then
        assertThatThrownBy(() -> filterWordService.createFilterWord(dto))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("관리자 계정만 접근 가능합니다.");
    }

    @DisplayName("필터 단어 등록")
    @Test
    void createFilterWord() {
        //given
        UserInfo admin = UserInfo.builder()
                .userId(1L)
                .userType('A')
                .userName("신성주")
                .schoolClassId(1L)
                .build();

        given(userServiceClient.searchUserInfo(anyString()))
                .willReturn(admin);

        CreateFilterWordDto dto = CreateFilterWordDto.builder()
                .loginUserToken("jwt")
                .word("심한욕")
                .build();
        //when
        Long filterWordId = filterWordService.createFilterWord(dto);
        //then
        assertThat(filterWordId).isPositive();
    }
}