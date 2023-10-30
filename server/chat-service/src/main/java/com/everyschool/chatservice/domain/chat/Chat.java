package com.everyschool.chatservice.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document(collection = "chat")
@Getter
@Setter
public class Chat {

    @Id
    @Field("chat_id")
    private Long id;

    @NotNull
    private Long userId;
    @NotBlank
    private String content;
    private Boolean isBad;

    @Field("chat_room_id")
    private Long chatRoomId;

    private boolean isDeleted;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    private Chat(Long id, Long userId, String content, Boolean isBad, Long chatRoomId) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.isBad = isBad;
        this.chatRoomId = chatRoomId;
        this.isDeleted = false;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }
}
