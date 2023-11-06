package com.everyschool.chatservice.api.service.chat;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.response.ChatResponse;
import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.repository.ChatRepository;
import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatQueryService {

    private final ChatRepository chatRepository;
    private final UserServiceClient userServiceClient;

    public List<ChatResponse> searchChat(Long chatRoomId, @Nullable Long idx, String token) {
        UserInfo loginUser = userServiceClient.searchUserInfo(token);

        if (idx == null) {
            idx = Long.MAX_VALUE;
        }
        List<Chat> list = chatRepository.findTop20ChatsByChatRoomIdAndIsBadAndIdLessThanOrderByIdDesc(chatRoomId, false, idx);
        List<ChatResponse> responses = new ArrayList<>();
        for (Chat chat : list) {
            ChatResponse response = createChatResponse(loginUser, chat);
            responses.add(response);
        }
        return responses;
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
