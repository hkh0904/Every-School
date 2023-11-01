package com.everyschool.chatservice.api.service.filterword;

import com.everyschool.chatservice.IntegrationTestSupport;
import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.request.ChatMessage;
import com.everyschool.chatservice.api.controller.filterword.response.ChatFilterResponse;
import com.everyschool.chatservice.api.service.filterword.dto.CreateFilterWordDto;
import com.everyschool.chatservice.domain.filterword.FilterWord;
import com.everyschool.chatservice.domain.filterword.repository.FilterWordRepository;
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

    @Autowired
    private FilterWordRepository filterWordRepository;

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

    @DisplayName("메세지 보낼 때 채팅 등록하고 필터 적용하여 전송 가능 여부 출력 (실패)")
    @Test
    void sendMessageFail() {
        //given
        UserInfo sender = UserInfo.builder()
                .userId(1L)
                .userType('A')
                .userName("신성주")
                .schoolClassId(1L)
                .build();

        given(userServiceClient.searchUserInfoByUserKey(anyString()))
                .willReturn(sender);

        ChatMessage message = ChatMessage.builder()
                .chatRoomId(1L)
                .senderUserKey("senderUserKey")
                .message("비속어")
                .build();

        FilterWord filterWord = FilterWord.builder()
                .word("비속어")
                .build();

        filterWordRepository.save(filterWord);

        //when
        ChatFilterResponse response = filterWordService.sendMessage(message);

        //then
        assertThat(response.getIsBad()).isEqualTo(true);
    }

    @DisplayName("메세지 보낼 때 채팅 등록하고 필터 적용하여 전송 가능 여부 출력 (성공)")
    @Test
    void sendMessage() {
        //given
        UserInfo sender = UserInfo.builder()
                .userId(1L)
                .userType('A')
                .userName("신성주")
                .schoolClassId(1L)
                .build();

        given(userServiceClient.searchUserInfoByUserKey(anyString()))
                .willReturn(sender);

        ChatMessage message = ChatMessage.builder()
                .chatRoomId(1L)
                .senderUserKey("senderUserKey")
                .message("평범한 대화")
                .build();

        //when
        ChatFilterResponse response = filterWordService.sendMessage(message);

        //then
        assertThat(response.getIsBad()).isEqualTo(false);
    }
}