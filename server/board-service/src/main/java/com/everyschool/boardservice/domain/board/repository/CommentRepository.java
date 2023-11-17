package com.everyschool.boardservice.domain.board.repository;

import com.everyschool.boardservice.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoardId(Long boardId);

    @Query("select c.anonymousNum from Comment c where c.userId=:userId and c.board.id=:boardId")
    Optional<Integer> findAnonymousNumByUserIdAndBoardId(@Param("userId") Long userId, @Param("boardId") Long boardId);
}
