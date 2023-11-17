package com.everyschool.chatservice.domain.chatroomuser.repository;

import com.everyschool.chatservice.domain.chatroomuser.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
}
