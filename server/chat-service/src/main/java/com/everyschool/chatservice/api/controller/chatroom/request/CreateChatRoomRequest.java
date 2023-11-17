package com.everyschool.chatservice.api.controller.chatroom.request;

import com.everyschool.chatservice.api.service.chatroom.dto.CreateChatRoomDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateChatRoomRequest {

    @NotBlank
    private String opponentUserKey;
    private String opponentUserName;
    private String opponentUserType;
    @NotNull
    private int loginUserType;
    @NotNull
    private Long schoolClassId;

    @Builder
    private CreateChatRoomRequest(String opponentUserKey, String opponentUserName, String opponentUserType, int loginUserType, Long schoolClassId) {
        this.opponentUserKey = opponentUserKey;
        this.opponentUserName = opponentUserName;
        this.opponentUserType = opponentUserType;
        this.loginUserType = loginUserType;
        this.schoolClassId = schoolClassId;
    }

    public CreateChatRoomDto toDto(String token) {
        return CreateChatRoomDto.builder()
                .loginUserToken(token)
                .opponentUserKey(this.opponentUserKey)
                .schoolClassId(this.schoolClassId)
                .opponentUserName(this.opponentUserName)
                .opponentUserType(this.opponentUserType)
                .loginUserType(this.loginUserType)
                .build();
    }
}
