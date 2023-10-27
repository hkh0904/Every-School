package com.everyschool.chatservice.domain.chat;

import com.everyschool.chatservice.domain.BaseEntity;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @NotNull
    private Long userId;
    @NotBlank
    private String content;
    private Boolean isBad;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    protected Chat() {
        super();
    }

    @Builder
    private Chat(Long id, Long userId, String content, Boolean isBad, ChatRoom chatRoom) {
        this();
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.isBad = isBad;
        this.chatRoom = chatRoom;
    }
}
