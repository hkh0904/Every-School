package com.everyschool.openaiservice.api.client.response.chat;

import com.everyschool.openaiservice.api.service.dto.Chat;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CheckingChatResponse {

    private Long teacherId;
    private String teacherName;
    private List<Chat> chats;
    private String otherUserName;
    private String childName;

    @Builder
    private CheckingChatResponse(Long teacherId, String teacherName, List<Chat> chats, String otherUserName, String childName) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.chats = chats;
        this.otherUserName = otherUserName;
        this.childName = childName;
    }
}
