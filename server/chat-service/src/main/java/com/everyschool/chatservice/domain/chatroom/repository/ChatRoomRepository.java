package com.everyschool.chatservice.domain.chatroom.repository;

import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
