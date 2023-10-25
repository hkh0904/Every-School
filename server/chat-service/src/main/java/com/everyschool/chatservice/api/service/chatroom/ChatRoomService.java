package com.everyschool.chatservice.api.service.chatroom;

import com.everyschool.chatservice.api.controller.chat.response.CreateChatRoomResponse;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public CreateChatRoomResponse createChatRoom() {
        // TODO: 2023-10-25 로그인 한 회원 정보 요청
        // TODO: 2023-10-25 상대 유저키로 정보 요청
        // TODO: 2023-10-25 채팅방 생성
        // TODO: 2023-10-25 채팅방 회원 등록
        return null;
    }
}
