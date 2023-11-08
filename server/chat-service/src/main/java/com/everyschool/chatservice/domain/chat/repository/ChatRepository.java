package com.everyschool.chatservice.domain.chat.repository;

import com.everyschool.chatservice.domain.chat.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, Long> {

    List<Chat> findTop20ChatsByChatRoomIdAndStatusAndIdLessThanOrderByIdDesc(Long chatRoomId, int status, Long id);

    List<Chat> findByCreatedDateBetween(LocalDateTime startTime, LocalDateTime endTime);
}
