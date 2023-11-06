package com.everyschool.chatservice.domain.chat.repository;

import com.everyschool.chatservice.domain.chat.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, Long> {

    List<Chat> findTop20ChatsByChatRoomIdAndStatusAndIdLessThanOrderByIdDesc(Long chatRoomId, Boolean isBad, Long id);
}
