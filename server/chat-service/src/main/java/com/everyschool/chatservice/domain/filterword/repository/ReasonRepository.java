package com.everyschool.chatservice.domain.filterword.repository;

import com.everyschool.chatservice.domain.filterword.Reason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReasonRepository extends JpaRepository<Reason, Long> {

    Optional<Reason> findByChatId(Long chatId);
}
