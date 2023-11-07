package com.everyschool.chatservice.domain.mongo.repository;

import com.everyschool.chatservice.domain.mongo.DatabaseSequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseSequenceRepository extends MongoRepository<DatabaseSequence, String> {

}
