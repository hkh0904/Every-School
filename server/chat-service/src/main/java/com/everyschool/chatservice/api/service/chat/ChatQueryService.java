package com.everyschool.chatservice.api.service.chat;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.response.*;
import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.ChatReview;
import com.everyschool.chatservice.domain.chat.ChatStatus;
import com.everyschool.chatservice.domain.chat.repository.ChatRepository;
import com.everyschool.chatservice.domain.chat.repository.ChatReviewQueryRepository;
import com.everyschool.chatservice.domain.chat.repository.ChatReviewRepository;
import com.everyschool.chatservice.domain.filterword.Reason;
import com.everyschool.chatservice.domain.filterword.repository.ReasonRepository;
import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.everyschool.chatservice.domain.chat.ChatStatus.WARNING;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatQueryService {

    private final ChatRepository chatRepository;
    private final ChatReviewRepository chatReviewRepository;
    private final ChatReviewQueryRepository chatReviewQueryRepository;
    private final ReasonRepository reasonRepository;
    private final UserServiceClient userServiceClient;

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
        List<Chat> list = chatRepository.findTop20ChatsByChatRoomIdAndStatusNotAndIdLessThanOrderByIdDesc(chatRoomId, ChatStatus.BAD.getCode(), idx);
        log.debug("[Service] 채팅 목록 불러오기 요청됨. 채팅 리스트 수 = {}", list.size());
        List<ChatResponse> responses = new ArrayList<>();
        for (Chat chat : list) {
            ChatResponse response = createChatResponse(loginUser, chat);
            responses.add(response);
        }
        return responses;
    }

    public WarningChatResponse searchReviewChat(Long chatRoomId, Long reviewId, LocalDate date, String token) {
        log.debug("[Service] 악성 의심 채팅 목록 불러오기 요청됨. 채팅방 Id = {}", chatRoomId);
        log.debug("[Service] 악성 의심 채팅 목록 불러오기 요청됨. 채팅 날짜 = {}", date.toString());
        UserInfo loginUser = userServiceClient.searchUserInfo(token);
        List<Chat> chats = chatRepository.findByCreatedDateBetweenAndChatRoomIdAndStatusIsNotOrderByCreatedDateDesc(date.atStartOfDay(), date.plusDays(1).atStartOfDay(), chatRoomId, ChatStatus.BAD.getCode());
        log.debug("[Service] 악성 의심 채팅 목록 불러오기 요청됨. 채팅 리스트 수 = {}", chats.size());
        List<WarningChat> warningChats = new ArrayList<>();
        for (Chat chat : chats) {
            createWarningChatResponse(loginUser, warningChats, chat);
        }

        ChatReview chatReview = chatReviewRepository.findById(reviewId).orElseThrow(()
                -> new NoSuchElementException("잘못된 요청입니다."));

        return WarningChatResponse.builder()
                .chatList(warningChats)
                .title(chatReview.getTitle())
                .build();
    }

    private void createWarningChatResponse(UserInfo loginUser, List<WarningChat> responses, Chat chat) {
        int status = chat.getStatus();
        String warningReason = "";
        if (status == WARNING.getCode()) {
            Reason reason = reasonRepository.findByChatId(chat.getId()).orElseThrow(()
                    -> new NoSuchElementException("잘못된 요청입니다."));

            warningReason = reason.getFilterReason();
        }
        WarningChat response = WarningChat.builder()
                .chatId(chat.getId())
                .teacherSend(chat.getUserId().equals(loginUser.getUserId()))
                .content(chat.getContent())
                .sendTime(chat.getCreatedDate())
                .chatStatus(ChatStatus.getText(status))
                .reason(warningReason)
                .build();
        responses.add(response);
    }

    /**
     * 해당 날짜의 문제있던 채팅방 제목 불러오기
     */
    public List<ChatReviewResponse> searchReviewChatDate(LocalDate date) {
        return chatReviewQueryRepository.searchReviewChatDate(date);
    }

    /**
     * 사용자의 문제 의심 채팅 내역 불러오기
     *
     * @param token
     * @return
     */
    public List<WarningChatReviewResponse> searchReviewChatList(String token) {
        log.debug("[문제 채팅 목록 조회] Service");
        UserInfo loginUser = userServiceClient.searchUserInfo(token);
        return chatReviewQueryRepository.searchReviewChatList(loginUser.getUserId());
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
