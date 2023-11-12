package com.everyschool.chatservice.api.service.filterword;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.request.ChatMessage;
import com.everyschool.chatservice.api.controller.filterword.response.ChatFilterResponse;
import com.everyschool.chatservice.api.service.SequenceGeneratorService;
import com.everyschool.chatservice.api.service.filterword.dto.CreateFilterWordDto;
import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.ChatStatus;
import com.everyschool.chatservice.domain.chat.repository.ChatRepository;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserQueryRepository;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserRepository;
import com.everyschool.chatservice.domain.filterword.FilterWord;
import com.everyschool.chatservice.domain.filterword.Reason;
import com.everyschool.chatservice.domain.filterword.repository.FilterWordRepository;
import com.everyschool.chatservice.domain.filterword.repository.ReasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FilterWordService {

    private final FilterWordRepository filterWordRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatRoomUserQueryRepository chatRoomUserQueryRepository;
    private final ReasonRepository reasonRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    private final UserServiceClient userServiceClient;

    /**
     * 필터 단어 등록
     *
     * @param dto
     * @return
     */
    public Long createFilterWord(CreateFilterWordDto dto) {
        UserInfo loginUser = userServiceClient.searchUserInfo(dto.getLoginUserToken());
        if (loginUser.getUserType() != 'A') {
            throw new IllegalArgumentException("관리자 계정만 접근 가능합니다.");
        }

        FilterWord saved = saveFilterWord(dto);
        return saved.getId();
    }

    /**
     * 메세지 보낼 때 채팅 등록하고 필터 적용하여 전송 가능 여부 출력
     *
     * @param message
     * @return
     */
    public ChatFilterResponse sendMessage(ChatMessage message) {
        log.debug("[Service] 채팅 필터링 senderUserKey = {}", message.getMessage());
        UserInfo senderUserInfo = userServiceClient.searchUserInfoByUserKey(message.getSenderUserKey());
        List<String> reasons = new ArrayList<>();
        ChatStatus chatStatus = isBadChat(message, reasons);

        Chat chat = saveChat(message, senderUserInfo, chatStatus.getCode());
        List<ChatRoomUser> roomUsers = chatRoomUserQueryRepository.findChatRoomUsersByChatRoomId(message.getChatRoomId());

        if (chatStatus == ChatStatus.PLANE) {
            roomUsers.get(0).updateUpdateChat(message.getMessage());
            roomUsers.get(1).updateUpdateChat(message.getMessage());
        }

        return getFilterResultResponse(reasons, chatStatus, chat);
    }

    private Chat saveChat(ChatMessage message, UserInfo senderUserInfo, int status) {
        Chat chat = Chat.builder()
                .id(sequenceGeneratorService.generateSequence(Chat.SEQUENCE_NAME))
                .userId(senderUserInfo.getUserId())
                .content(message.getMessage())
                .status(status)
                .chatRoomId(message.getChatRoomId())
                .build();
        return chatRepository.save(chat);
    }

    private ChatFilterResponse getFilterResultResponse(List<String> reasons, ChatStatus chatStatus, Chat chat) {
        ChatFilterResponse response = ChatFilterResponse.builder()
                .isBad(false)
                .reason("")
                .build();
        if (chatStatus == ChatStatus.BAD) {
            for (String reason : reasons) {
                saveReason(chat, reason);
            }
            response.setReason("비속어가 포함되어 있습니다.");
            response.setIsBad(true);
        }
        return response;
    }

    private void saveReason(Chat chat, String reason) {
        Reason filterReason = Reason.builder()
                .chatId(chat.getId())
                .filterReason(reason)
                .build();
        reasonRepository.save(filterReason);
    }

    private ChatStatus isBadChat(ChatMessage message, List<String> reasons) {
        boolean isBad = false;
        List<FilterWord> filters = filterWordRepository.findAll();
        for (FilterWord filter : filters) {
            if (message.getMessage().contains(filter.getWord())) {
                reasons.add(filter.getWord());
                isBad = true;
            }
        }
        if (isBad) {
            return ChatStatus.BAD;
        }
        return ChatStatus.PLANE;
    }

    private FilterWord saveFilterWord(CreateFilterWordDto dto) {
        FilterWord filterWord = dto.toEntity();
        filterWord.setId(sequenceGeneratorService.generateSequence(FilterWord.SEQUENCE_NAME));
        return filterWordRepository.save(filterWord);
    }
}
