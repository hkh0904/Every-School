package com.everyschool.chatservice.api.service.chat;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.response.ChatResponse;
import com.everyschool.chatservice.api.controller.chat.response.ChatReviewResponse;
import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.ChatStatus;
import com.everyschool.chatservice.domain.chat.repository.ChatRepository;
import com.everyschool.chatservice.domain.chat.repository.ChatReviewQueryRepository;
import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatQueryService {

    private final ChatRepository chatRepository;
    private final UserServiceClient userServiceClient;
    private final ChatReviewQueryRepository chatReviewQueryRepository;

    /**
     * 채팅 목록 가져오기
     *
     * @param chatRoomId
     * @param idx
     * @param token
     * @return
     */
    public List<ChatResponse> searchChat(Long chatRoomId, @Nullable Long idx, String token) {
        UserInfo loginUser = userServiceClient.searchUserInfo(token);
        log.debug("[Service] 채팅 목록 불러오기 요청됨. 채팅방 Id = {}", chatRoomId);
        log.debug("[Service] 채팅 목록 불러오기 요청됨. Idx = {}", idx);
        if (idx == null) {
            idx = Long.MAX_VALUE;
        }
        List<Chat> list = chatRepository.findTop20ChatsByChatRoomIdAndStatusAndIdLessThanOrderByIdDesc(chatRoomId, ChatStatus.PLANE.getCode(), idx);
        log.debug("[Service] 채팅 목록 불러오기 요청됨. 채팅 리스트 수 = {}", list.size());
        List<ChatResponse> responses = new ArrayList<>();
        for (Chat chat : list) {
            ChatResponse response = createChatResponse(loginUser, chat);
            responses.add(response);
        }
        return responses;
    }

    /**
     * 해당 날짜의 문제있던 채팅방 제목 불러오기
     */
    public List<ChatReviewResponse> searchReviewChatDate(LocalDate date) {
        return chatReviewQueryRepository.searchReviewChatDate(date);
    }

    private static ChatResponse createChatResponse(UserInfo loginUser, Chat chat) {
        return ChatResponse.builder()
                .chatId(chat.getId())
                .isMine(Objects.equals(loginUser.getUserId(), chat.getUserId()))
                .content(chat.getContent())
                .sendTime(chat.getCreatedDate())
                .build();
    }
}
