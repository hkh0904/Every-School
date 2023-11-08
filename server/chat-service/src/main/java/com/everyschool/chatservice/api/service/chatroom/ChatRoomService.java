package com.everyschool.chatservice.api.service.chatroom;

import com.everyschool.chatservice.api.client.SchoolServiceClient;
import com.everyschool.chatservice.api.client.UserServiceClient;
import com.everyschool.chatservice.api.client.response.SchoolClassInfo;
import com.everyschool.chatservice.api.client.response.UserInfo;
import com.everyschool.chatservice.api.controller.chat.response.CreateChatRoomResponse;
import com.everyschool.chatservice.api.service.chatroom.dto.CreateChatRoomDto;
import com.everyschool.chatservice.api.service.util.RedisUtils;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomRepository;
import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserQueryRepository;
import com.everyschool.chatservice.domain.chatroomuser.repository.ChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatRoomUserQueryRepository chatRoomUserQueryRepository;

    private final UserServiceClient userServiceClient;
    private final SchoolServiceClient schoolServiceClient;

    private final RedisUtils redisUtil;

    /**
     * 채팅방 생성
     *
     * @param dto
     * @return 채팅방 Id, 채팅방 이름, 상대 유저 이름
     */
    public CreateChatRoomResponse createChatRoom(CreateChatRoomDto dto) {

        // 로그인 한 회원 정보 요청
        UserInfo loginUser = userServiceClient.searchUserInfo(dto.getLoginUserToken());
        log.debug("[채팅방 생성 Service] 로그인 유저 = {}", loginUser.getUserName());
        // 상대 유저키로 정보 요청
        UserInfo opponentUser = userServiceClient.searchUserInfoByUserKey(dto.getOpponentUserKey());
        log.debug("[채팅방 생성 Service] 상대방 유저 = {}", opponentUser.getUserName());

        // TODO: 2023-10-27 두 아이디로 생성된 방 있으면 그냥 열기

        // 채팅방 생성
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder().build());

        // 학급 id로 학급 이름(1학년 1반) 가져오기
        SchoolClassInfo schoolClassInfo = schoolServiceClient.searchSchoolClassInfo(dto.getSchoolClassId());
        String className = schoolClassInfo.getClassName();
        log.debug("[채팅방 생성 Service] 학급 = {}", className);

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
     * 채팅방 입장시 채팅방에 있는 인원 증가
     *
     * @param chatRoomId
     * @param userId
     */
    public void connectChatRoom(Long chatRoomId, Long userId) {
        String chatRoomUserCountKey = "CHAR_ROOM_USER_COUNT_" + chatRoomId;
        String roomUserCount = redisUtil.getString(chatRoomUserCountKey);
        if (roomUserCount == null) {
            roomUserCount = String.valueOf(0);
        }
        int count = Integer.parseInt(roomUserCount) + 1;
        redisUtil.insertString(chatRoomUserCountKey, String.valueOf(count));

        //채팅 읽음 처리
        ChatRoomUser chatRoomUser = chatRoomUserQueryRepository.findChatRoomUserByRoomIdAndUserId(chatRoomId, userId)
                .orElseThrow(() -> new NoSuchElementException("채팅방이 존재하지 않습니다."));
        chatRoomUser.read();
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

        log.debug("[채팅방 생성] 제목 : {}", opponentTitle);
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
    private ChatRoomUser createChatRoomUser(String title, Long userId, ChatRoom chatRoom, String opponentUserType) {
        return ChatRoomUser.builder()
                .chatRoomTitle(title)
                .userId(userId)
                .opponentUserType(opponentUserType)
                .isAlarm(true)
                .unreadCount(0)
                .chatRoom(chatRoom)
                .build();
    }

    public void disconnect(Long chatRoomId) {
        String chatRoomUserCountKey = "CHAR_ROOM_USER_COUNT_" + chatRoomId;
        String roomUserCount = redisUtil.getString(chatRoomUserCountKey);
        if (roomUserCount == null) {
            roomUserCount = String.valueOf(0);
        }
        int count = Integer.parseInt(roomUserCount) - 1;
        redisUtil.insertString(chatRoomUserCountKey, String.valueOf(count));
    }
}
