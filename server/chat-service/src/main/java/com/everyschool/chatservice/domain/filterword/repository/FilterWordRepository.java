package com.everyschool.chatservice.domain.filterword.repository;

import com.everyschool.chatservice.domain.filterword.FilterWord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterWordRepository extends MongoRepository<FilterWord, Long> {
}
