package com.everyschool.chatservice.api.service.chatroom.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateChatRoomDto {

    private String loginUserToken;
    private String opponentUserKey;
    private String opponentUserName;
    private String opponentUserType;
    private Long schoolClassId;
    private int loginUserType;

    @Builder
    private CreateChatRoomDto(String loginUserToken, String opponentUserKey, String opponentUserName, String opponentUserType, Long schoolClassId, int loginUserType) {
        this.loginUserToken = loginUserToken;
        this.opponentUserKey = opponentUserKey;
        this.opponentUserName = opponentUserName;
        this.opponentUserType = opponentUserType;
        this.schoolClassId = schoolClassId;
        this.loginUserType = loginUserType;
    }

}
