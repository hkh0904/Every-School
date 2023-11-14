package com.everyschool.boardservice.domain.board.repository;

import com.everyschool.boardservice.domain.board.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
