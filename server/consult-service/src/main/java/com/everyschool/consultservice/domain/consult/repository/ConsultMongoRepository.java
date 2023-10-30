package com.everyschool.consultservice.domain.consult.repository;

import com.everyschool.consultservice.domain.consult.ConsultParentDocs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultMongoRepository extends MongoRepository<ConsultParentDocs, Long> {
}
