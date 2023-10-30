package com.everyschool.chatservice.domain.chat.repository;

import com.everyschool.chatservice.domain.chat.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, Long> {
}
