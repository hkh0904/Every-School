package com.everyschool.chatservice.domain.chat;

import com.everyschool.chatservice.domain.BaseEntity;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_review_id")
    private Long id;

    private LocalDate chatDate;
    @Column(length = 50)
    @Size(max = 50)
    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Builder
    private ChatReview(Long id, LocalDate chatDate, String title, ChatRoom chatRoom) {
        this.id = id;
        this.chatDate = chatDate;
        this.title = title;
        this.chatRoom = chatRoom;
    }
}
