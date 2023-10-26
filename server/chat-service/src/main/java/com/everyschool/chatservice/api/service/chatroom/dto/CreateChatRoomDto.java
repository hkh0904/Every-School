package com.everyschool.chatservice.api.service.chatroom.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CreateChatRoomDto {

    private String loginUserToken;
    private String opponentUserKey;
    private String relation;
    private Long schoolClassId;

    @Builder
    private CreateChatRoomDto(String loginUserToken, String opponentUserKey, String relation, Long schoolClassId) {
        this.loginUserToken = loginUserToken;
        this.opponentUserKey = opponentUserKey;
        this.relation = relation;
        this.schoolClassId = schoolClassId;
    }
}
