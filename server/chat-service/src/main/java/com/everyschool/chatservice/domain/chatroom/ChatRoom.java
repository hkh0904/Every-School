package com.everyschool.chatservice.domain.chatroom;

import com.everyschool.chatservice.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Builder
    private ChatRoom(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
