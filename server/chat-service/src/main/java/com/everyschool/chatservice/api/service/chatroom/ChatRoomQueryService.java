package com.everyschool.chatservice.api.service.chatroom;

import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.response.ChatRoomListResponse;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomQueryService {

    private final ChatRoomQueryRepository chatRoomQueryRepository;
    private final UserServiceClient userServiceClient;

    /**
     * 생성된 채팅방 목록 불러오기
     *
     * @param token
     * @return
     */
    public List<ChatRoomListResponse> searchChatRooms(String token) {

        UserInfo loginUser = userServiceClient.searchUserInfo(token);
        return chatRoomQueryRepository.findChatRooms(loginUser.getUserId());
    }
}
