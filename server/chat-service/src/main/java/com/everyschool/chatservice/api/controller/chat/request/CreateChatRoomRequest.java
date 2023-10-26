package com.everyschool.chatservice.api.controller.chat.request;

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
    @NotNull
    private Character loginUserType;
    @NotNull
    private Long schoolClassId;
    @NotBlank
    private String relation;

    @Builder
    private CreateChatRoomRequest(String opponentUserKey, Character loginUserType, Long schoolClassId, String relation) {
        this.opponentUserKey = opponentUserKey;
        this.loginUserType = loginUserType;
        this.schoolClassId = schoolClassId;
        this.relation = relation;
    }

    public CreateChatRoomDto toDto(String token) {
        return CreateChatRoomDto.builder()
                .loginUserToken(token)
                .opponentUserKey(this.opponentUserKey)
                .relation(this.relation)
                .schoolClassId(this.schoolClassId)
                .build();
    }
}
