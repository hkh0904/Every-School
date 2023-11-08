package com.everyschool.openaiservice.api.service;

import com.everyschool.openaiservice.api.client.ChatServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class OpenAiService {

    private final ChatServiceClient chatServiceClient;

    public void doChecking() {
        // TODO: 2023-11-08 채팅방 id 가져오기
        List<Long> roomIds = chatServiceClient.searchChatRoomIdByDate(LocalDate.now().minusDays(1));
        // TODO: 2023-11-08 반복문 안에서 채팅 목록 가져오기
        // TODO: 2023-11-08 발신자 정리해서 GPT 보내기
        // TODO: 2023-11-08 문제 채팅 카프카로 저장하기
    }
}


