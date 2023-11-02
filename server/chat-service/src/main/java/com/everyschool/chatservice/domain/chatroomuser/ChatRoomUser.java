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

import static javax.persistence.FetchType.LAZY;

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
    @Column(length = 50)
    @Size(max = 50)
    private String lastContent;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Builder
    private ChatRoomUser(Long id, String chatRoomTitle, String socketTopic, Long userId, boolean isAlarm, int unreadCount, String lastContent, ChatRoom chatRoom) {
        this.id = id;
        this.chatRoomTitle = chatRoomTitle;
        this.socketTopic = socketTopic;
        this.userId = userId;
        this.isAlarm = isAlarm;
        this.unreadCount = unreadCount;
        this.lastContent = lastContent;
        this.chatRoom = chatRoom;
    }

    public void updateUpdateChat(String lastContent) {
        if (lastContent.length() >= 50) {
            lastContent = lastContent.substring(0, 49);
        }
        this.lastContent = lastContent;
        this.unreadCount++;
    }

    public void read() {
        this.unreadCount = 0;
    }
}
