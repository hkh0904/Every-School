package com.everyschool.chatservice.domain.chat.repository;

import com.everyschool.chatservice.domain.chat.ChatReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatReviewRepository extends JpaRepository<ChatReview, Long> {

    Optional<ChatReview> findById(Long id);
}
