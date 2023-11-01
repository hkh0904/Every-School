package com.everyschool.boardservice.domain.board.repository;

import com.everyschool.boardservice.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
