package com.everyschool.chatservice.api.service.chatroom;

import com.everyschool.chatservice.api.client.SchoolServiceClient;
import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.response.CreateChatRoomResponse;
import com.everyschool.chatservice.api.service.chatroom.dto.CreateChatRoomDto;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomRepository;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final UserServiceClient userServiceClient;
    private final SchoolServiceClient schoolServiceClient;

    /**
     * 채팅방 생성
     *
     * @param dto
     * @return 채팅방 Id, 채팅방 이름, 상대 유저 이름
     */
    public CreateChatRoomResponse createChatRoom(CreateChatRoomDto dto) {

        // 로그인 한 회원 정보 요청
        UserInfo loginUser = userServiceClient.searchUserInfo(dto.getLoginUserToken());
        // 상대 유저키로 정보 요청
        UserInfo opponentUser = userServiceClient.searchUserInfoByUserKey(dto.getOpponentUserKey());

        // 채팅방 생성
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder().build());

        // 학급 id로 학급 이름(1학년 1반) 가져오기
        String className = schoolServiceClient.searchClassName(dto.getSchoolClassId());

        // 상대방의 체팅방 이름 만들기
        String opponentTitle = getOpponentTitle(dto.getSchoolClassId(), loginUser, className);

        ChatRoomUser opponentRoom = createChatRoomUser(opponentTitle, opponentUser.getUserId(), chatRoom);
        ChatRoomUser loginUserRoom = createChatRoomUser(dto.getRelation(), loginUser.getUserId(), chatRoom);

        ChatRoomUser roomUser = chatRoomUserRepository.save(loginUserRoom);
        chatRoomUserRepository.save(opponentRoom);

        return CreateChatRoomResponse.builder()
                .roomId(chatRoom.getId())
                .roomTitle(roomUser.getChatRoomTitle())
                .userName(opponentUser.getUserName())
                .build();
    }

    /**
     * 상대방 방 제목 생성
     *
     * @param schoolClassId
     * @param loginUser
     * @param className
     * @return
     */
    private String getOpponentTitle(Long schoolClassId, UserInfo loginUser, String className) {
        String opponentTitle;
        if (loginUser.getUserType() == 'T') {
            opponentTitle = className + " " + loginUser.getUserName() + " 선생님";
        } else if (loginUser.getUserType() == 'S') {
            opponentTitle = className + " " + loginUser.getUserName() + " 학생";
        } else {
            // 부모와 학급키로 학급에 다니는 자녀 이름 요청
            String childName = userServiceClient.searchChildName(loginUser.getUserId(), schoolClassId);
            if (loginUser.getUserType() == 'M') {
                opponentTitle = className + " " + childName + "(모)";
            } else {
                opponentTitle = className + " " + childName + "(부)";
            }
        }
        return opponentTitle;
    }

    /**
     * 채팅방 유저 Entity 생성
     *
     * @param title
     * @param userId
     * @param chatRoom
     * @return
     */
    private ChatRoomUser createChatRoomUser(String title, Long userId, ChatRoom chatRoom) {
        return ChatRoomUser.builder()
                .chatRoomTitle(title)
                .socketTopic("/topic/chatroom/" + chatRoom.getId())
                .userId(userId)
                .isAlarm(true)
                .unreadCount(0)
                .chatRoom(chatRoom)
                .build();
    }
}
