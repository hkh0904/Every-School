package com.everyschool.chatservice.domain.chatroomuser;

import com.everyschool.chatservice.domain.BaseEntity;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_user_id")
    private Long id;

    @Column(length = 50, nullable = false)
    @Size(max = 50)
    private String chatRoomTitle;
    @Column(length = 20)
    @Size(max = 20)
    private String socketTopic;

    @NotNull
    private Long userId;
    private boolean isAlarm;
    private int unreadCount;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Builder
    private ChatRoomUser(Long id, String chatRoomTitle, String socketTopic, Long userId, boolean isAlarm, int unreadCount, ChatRoom chatRoom) {
        this.id = id;
        this.chatRoomTitle = chatRoomTitle;
        this.socketTopic = socketTopic;
        this.userId = userId;
        this.isAlarm = isAlarm;
        this.unreadCount = unreadCount;
        this.chatRoom = chatRoom;
    }
}
